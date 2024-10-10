package az.orient.eshop.service.impl;

import az.orient.eshop.dto.request.ReqProduct;
import az.orient.eshop.dto.request.ReqProductDetails;
import az.orient.eshop.dto.response.*;
import az.orient.eshop.entity.*;
import az.orient.eshop.enums.Currency;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.enums.Gender;
import az.orient.eshop.enums.MediaTypeEnum;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.repository.*;
import az.orient.eshop.service.ProductService;
import az.orient.eshop.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final ProductDetailsRepository productDetailsRepository;
    private final ProductVideoRepository productVideoRepository;
    private final ProductImageRepository productImageRepository;
    private final Utility utility = new Utility();

    @Override
    public Response<RespProduct> addProduct(ReqProduct reqProduct) {
        Response<RespProduct> response = new Response<>();
        Product product = null;
        try {
            String name = reqProduct.getName();
            Long brandId = reqProduct.getBrandId();
            Long subcategoryId = reqProduct.getSubcategoryId();
            Gender.fromValue(reqProduct.getGender().getValue());
            if (name == null || brandId == null || subcategoryId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Brand brand = brandRepository.findByIdAndActive(brandId, EnumAvailableStatus.ACTIVE.getValue());
            if (brand == null) {
                throw new EshopException(ExceptionConstants.BRAND_NOT_FOUND, "Brand not found");
            }
            Subcategory subcategory = subcategoryRepository.findSubcategoryByIdAndActive(subcategoryId, EnumAvailableStatus.ACTIVE.getValue());
            if (subcategory == null) {
                throw new EshopException(ExceptionConstants.SUBCATEGORY_NOT_FOUND, "Subcategory not found");
            }
            product = Product.builder()
                    .name(name)
                    .productInformation(reqProduct.getProductInformation())
                    .brand(brand)
                    .gender(reqProduct.getGender())
                    .expertionDate(reqProduct.getExpertionDate())
                    .subcategory(subcategory)
                    .build();
            productRepository.save(product);
            List<ReqProductDetails> reqProductDetailsList = reqProduct.getReqProductDetailsList();
            List<ProductDetails> productDetailsList = addProductDetails(reqProductDetailsList, product.getId());
            product.setProductDetails(productDetailsList);
            productRepository.save(product);
            RespProduct respProduct = convertToRespProduct(product);
            response.setT(respProduct);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            if (product != null) {
                List<ProductDetails> productDetailsList = productDetailsRepository.findProductDetailsByProductIdAndActive(product.getId(), EnumAvailableStatus.ACTIVE.getValue());
                if (productDetailsList != null && !productDetailsList.isEmpty()) {
                    for (ProductDetails productDetails1 : productDetailsList) {
                        productDetails1.setActive(EnumAvailableStatus.DEACTIVE.getValue());
                        productDetailsRepository.save(productDetails1);
                    }
                }
                product.setActive(EnumAvailableStatus.DEACTIVE.getValue());
                productRepository.save(product);
            }
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            if (product != null) {
                List<ProductDetails> productDetailsList = productDetailsRepository.findProductDetailsByProductIdAndActive(product.getId(), EnumAvailableStatus.ACTIVE.getValue());
                if (productDetailsList != null && !productDetailsList.isEmpty()) {
                    for (ProductDetails productDetails1 : productDetailsList) {
                        productDetails1.setActive(EnumAvailableStatus.DEACTIVE.getValue());
                        productDetailsRepository.save(productDetails1);
                    }
                }
                product.setActive(EnumAvailableStatus.DEACTIVE.getValue());
                productRepository.save(product);
            }
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response<List<RespProduct>> getProductList() {
        Response<List<RespProduct>> response = new Response<>();
        try {
            List<Product> productList = productRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (productList.isEmpty()) {
                throw new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product is empty");
            }
            List<RespProduct> respProductList = productList.stream().map(this::convertToRespProduct).toList();
            response.setT(respProductList);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response<RespProduct> getProductById(Long id) {
        Response<RespProduct> response = new Response<>();
        try {
            if (id == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, " Id is null");
            }
            Product product = productRepository.findProductByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (product == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found");
            }
            RespProduct respProduct = convertToRespProduct(product);
            response.setT(respProduct);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response<RespProductDetails> getProductDetailsId(Long productDetailsId) {
        Response<RespProductDetails> response = new Response<>();
        try {
            if (productDetailsId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Product details id is null");
            }
            ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId, EnumAvailableStatus.ACTIVE.getValue());
            RespProductDetails respProductDetails = utility.convertToRespProductDetails(productDetails);
            response.setT(respProductDetails);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response<List<RespProductDetails>> getProductDetailsList() {
        Response<List<RespProductDetails>> response = new Response<>();
        try {
            List<ProductDetails> productDetailsList = productDetailsRepository.findProductDetailsByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (productDetailsList.isEmpty()) {
                throw new EshopException(ExceptionConstants.PRODUCT_DETAILS_NOT_FOUND, "Product Details not found");
            }
            List<RespProductDetails> respProductDetailsList = productDetailsList.stream().map(utility::convertToRespProductDetails).toList();
            response.setT(respProductDetailsList);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response<RespProduct> updateProduct(ReqProduct reqProduct) {
        Response<RespProduct> response = new Response<>();
        try {
            Long productId = reqProduct.getId();
            String name = reqProduct.getName();
            Long subcategoryId = reqProduct.getSubcategoryId();
            Gender.fromValue(reqProduct.getGender().getValue());
            Long brandId = reqProduct.getBrandId();
            if (productId == null || name == null || brandId == null || subcategoryId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Product product = productRepository.findProductByIdAndActive(productId, EnumAvailableStatus.ACTIVE.getValue());
            if (product == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found");
            }
            Brand brand = brandRepository.findByIdAndActive(brandId, EnumAvailableStatus.ACTIVE.getValue());
            if (brand == null) {
                throw new EshopException(ExceptionConstants.BRAND_NOT_FOUND, "Brand not found");
            }
            Subcategory subcategory = subcategoryRepository.findSubcategoryByIdAndActive(subcategoryId, EnumAvailableStatus.ACTIVE.getValue());
            if (subcategory == null) {
                throw new EshopException(ExceptionConstants.SUBCATEGORY_NOT_FOUND, "Subcategory not found");
            }
            List<ProductDetails> productDetailsList = updateProductDetails(reqProduct.getReqProductDetailsList(), productId);
            product.setName(name);
            product.setProductInformation(reqProduct.getProductInformation());
            product.setExpertionDate(reqProduct.getExpertionDate());
            product.setBrand(brand);
            product.setSubcategory(subcategory);
            product.setProductDetails(productDetailsList);
            product.setGender(reqProduct.getGender());
            productRepository.save(product);
            RespProduct respProduct = convertToRespProduct(product);
            response.setT(respProduct);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response deleteProduct(Long id) {
        Response response = new Response<>();
        try {
            if (id == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id is null");
            }
            Product product = productRepository.findProductByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (product == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found");
            }
            product.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            productRepository.save(product);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    private List<ProductDetails> addProductDetails(List<ReqProductDetails> reqProductDetailsList, Long productId) {
        List<ProductDetails> productDetailsList = new ArrayList<>();
        Product product = productRepository.findProductByIdAndActive(productId, EnumAvailableStatus.ACTIVE.getValue());
        for (ReqProductDetails reqProductDetails : reqProductDetailsList) {
            Long sizeId = reqProductDetails.getSizeId();
            Long colorId = reqProductDetails.getColorId();
            Integer stock = reqProductDetails.getStock();
            Currency currency = Currency.fromValue(reqProductDetails.getCurrency().getValue());
            Float price = reqProductDetails.getPrice();
            if (sizeId == null || stock == null || colorId == null || price == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            Size size = sizeRepository.findSizeByIdAndActive(sizeId, EnumAvailableStatus.ACTIVE.getValue());
            if (size == null) {
                throw new EshopException(ExceptionConstants.SIZE_NOT_FOUND, "Size not found");
            }
            Color color = colorRepository.findByIdAndActive(colorId, EnumAvailableStatus.ACTIVE.getValue());
            if (color == null) {
                throw new EshopException(ExceptionConstants.COLOR_NOT_FOUND, "Color not found");
            }
            ProductDetails productDetails = ProductDetails.builder()
                    .product(product)
                    .price(price)
                    .currency(currency)
                    .size(size)
                    .stock(stock)
                    .color(color)
                    .build();
            productDetailsRepository.save(productDetails);
        }
        return productDetailsList;
    }

    private List<ProductDetails> updateProductDetails(List<ReqProductDetails> reqProductDetailsList, Long productId) {
        List<ProductDetails> updatedProductDetailsList = new ArrayList<>();
        Product product = productRepository.findProductByIdAndActive(productId, EnumAvailableStatus.ACTIVE.getValue());
        if (product == null) {
            throw new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found");
        }
        for (ReqProductDetails reqProductDetails : reqProductDetailsList) {
            ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(reqProductDetails.getId(), EnumAvailableStatus.ACTIVE.getValue());
            if (productDetails == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_DETAILS_NOT_FOUND, "Product details not found");
            }
            Long sizeId = reqProductDetails.getSizeId();
            Long colorId = reqProductDetails.getColorId();
            Integer stock = reqProductDetails.getStock();
            Currency currency = Currency.fromValue(reqProductDetails.getCurrency().getValue());
            Float price = reqProductDetails.getPrice();
            if (sizeId == null || stock == null || colorId == null || price == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invaild request data");
            }
            Size size = sizeRepository.findSizeByIdAndActive(sizeId, EnumAvailableStatus.ACTIVE.getValue());
            if (size == null) {
                throw new EshopException(ExceptionConstants.SIZE_NOT_FOUND, "Size not found");
            }
            Color color = colorRepository.findByIdAndActive(colorId, EnumAvailableStatus.ACTIVE.getValue());
            if (color == null) {
                throw new EshopException(ExceptionConstants.COLOR_NOT_FOUND, "Color not found");
            }
            productDetails.setProduct(product);
            productDetails.setPrice(price);
            productDetails.setCurrency(currency);
            productDetails.setSize(size);
            productDetails.setStock(stock);
            productDetails.setColor(color);
            productDetailsRepository.save(productDetails);
        }
        return updatedProductDetailsList;
    }

    private void addProductMedia(Set<MultipartFile> files, Long productDetailsId, MediaTypeEnum mediaTypeEnum) throws IOException {
        if (productDetailsId == null) {
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Product details id is null");
        }
        ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId, EnumAvailableStatus.ACTIVE.getValue());
        if (productDetails == null) {
            throw new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product details not found");
        }
        for (MultipartFile file : files) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            if (fileName.contains("..")){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Filename contains invalid path sequence");
            }
            if (mediaTypeEnum == MediaTypeEnum.IMAGE){
                ProductImage productImage = new ProductImage(fileName,file.getContentType(),file.getBytes(),productDetails);
                productImageRepository.save(productImage);
            } else if  (mediaTypeEnum == MediaTypeEnum.VIDEO) {

            }

        }
    }

    private void updateProductMedia(Set<MultipartFile> files, Long productDetailsId, MediaTypeEnum mediaTypeEnum) throws IOException {
        if (productDetailsId == null) {
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Product details id is null");
        }
        ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId, EnumAvailableStatus.ACTIVE.getValue());
        if (productDetails == null) {
            throw new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product details not found");
        }
        if (mediaTypeEnum == MediaTypeEnum.IMAGE){
            productImageRepository.deactivateProductImagesByProductDetailsId(productDetailsId);
            addProductMedia(files,productDetailsId, MediaTypeEnum.IMAGE);
        } else if (mediaTypeEnum == MediaTypeEnum.VIDEO) {
            productVideoRepository.deactivateProductVideoByProductDetailsId(productDetailsId);
            addProductMedia(files,productDetailsId, MediaTypeEnum.VIDEO);
        }
    }

    private RespProduct convertToRespProduct(Product product) {
        List<RespProductDetails> respProductDetailsList = product.getProductDetails().stream().map(utility::convertToRespProductDetails).toList();
        RespCategory respCategory = RespCategory.builder()
                .id(product.getSubcategory().getCategory().getId())
                .name(product.getSubcategory().getCategory().getName())
                .build();
        RespSubcategory respSubcategory = RespSubcategory.builder()
                .id(product.getSubcategory().getId())
                .name(product.getSubcategory().getName())
                .respCategory(respCategory)
                .build();
        RespBrand respBrand = RespBrand.builder()
                .id(product.getBrand().getId())
                .name(product.getBrand().getName())
                .build();
        return RespProduct.builder()
                .id(product.getId())
                .name(product.getName())
                .respProductDetailsList(respProductDetailsList)
                .productInformation(product.getProductInformation())
                .respBrand(respBrand)
                .gender(product.getGender())
                .expertionDate(product.getExpertionDate())
                .respSubcategory(respSubcategory)
                .build();
    }
}
