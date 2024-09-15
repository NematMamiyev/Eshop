package az.orient.eshop.service.impl;

import az.orient.eshop.dto.request.ReqProduct;
import az.orient.eshop.dto.request.ReqProductDetails;
import az.orient.eshop.dto.request.ReqProductImage;
import az.orient.eshop.dto.request.ReqProductVideo;
import az.orient.eshop.dto.response.*;
import az.orient.eshop.entity.*;
import az.orient.eshop.enums.Currency;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.enums.Gender;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.repository.*;
import az.orient.eshop.service.ProductService;
import az.orient.eshop.utilty.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    private final Util util = new Util();

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
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request dataa");
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
            RespProductDetails respProductDetails = util.convertToRespProductDetails(productDetails);
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
            List<RespProductDetails> respProductDetailsList = productDetailsList.stream().map(util::convertToRespProductDetails).toList();
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
            Set<ReqProductImage> reqProductImageList = reqProductDetails.getReqProductImageList();
            if (reqProductImageList != null && !reqProductImageList.isEmpty()) {
                addProductImages(reqProductImageList, productDetails.getId());
            }
            Set<ReqProductVideo> reqProductVideoList = reqProductDetails.getReqProductVideoList();
            if (reqProductVideoList != null && !reqProductVideoList.isEmpty()) {
                addProductVideos(reqProductVideoList, productDetails.getId());
            }
            productDetailsRepository.save(productDetails);
            productDetailsList.add(productDetails);
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
            Set<ReqProductImage> reqProductImageList = reqProductDetails.getReqProductImageList();
            if (reqProductImageList != null && !reqProductImageList.isEmpty()) {
                updateProductImages(reqProductImageList, productDetails.getId());
            }
            Set<ReqProductVideo> reqProductVideoList = reqProductDetails.getReqProductVideoList();
            if (reqProductVideoList != null && !reqProductVideoList.isEmpty()) {
                updateProductVideos(reqProductVideoList, productDetails.getId());
            }

            updatedProductDetailsList.add(productDetails);
        }
        return updatedProductDetailsList;
    }

    private void addProductImages(Set<ReqProductImage> reqProductImageList, Long productDetailsId) {
        if (productDetailsId == null) {
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Product details id is null");
        }
        ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId, EnumAvailableStatus.ACTIVE.getValue());
        if (productDetails == null) {
            throw new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product details not found");
        }
        for (ReqProductImage reqProductImage : reqProductImageList) {
            byte[] data = reqProductImage.getData();
            String fileName = reqProductImage.getFileName();
            String fileType = reqProductImage.getFileType();

            if (data == null || data.length == 0) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Data is null or empty");
            }
            if (fileName == null || fileName.trim().isEmpty()) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "File name is null or empty");
            }
            if (fileType == null || fileType.trim().isEmpty()) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "File type is null or empty");
            }
            ProductImage productImage = ProductImage.builder()
                    .data(data)
                    .productDetails(productDetails)
                    .fileName(fileName)
                    .fileType(fileType)
                    .build();
            productImageRepository.save(productImage);
        }
    }

    private void updateProductImages(Set<ReqProductImage> reqProductImageSet, Long productDetailsId) {
        if (productDetailsId == null) {
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Product details id is null");
        }
        ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId, EnumAvailableStatus.ACTIVE.getValue());
        if (productDetails == null) {
            throw new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product details not found");
        }
        Set<ProductImage> existingImages = productImageRepository.findProductImageByProductDetailsIdAndActive(productDetailsId, EnumAvailableStatus.ACTIVE.getValue());
        Set<Long> existingImageIds = existingImages.stream()
                .map(ProductImage::getId)
                .collect(Collectors.toSet());
        Set<Long> newImageIds = reqProductImageSet.stream()
                .map(ReqProductImage::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        for (ReqProductImage reqProductImage : reqProductImageSet) {
            byte[] data = reqProductImage.getData();
            String fileName = reqProductImage.getFileName();
            String fileType = reqProductImage.getFileType();
            Long imageId = reqProductImage.getId();

            if (data == null || data.length == 0) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Data is null or empty");
            }
            if (fileName == null || fileName.trim().isEmpty()) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "File name is null or empty");
            }
            if (fileType == null || fileType.trim().isEmpty()) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "File type is null or empty");
            }
            if (imageId == null) {
                ProductImage productImage = ProductImage.builder()
                        .data(data)
                        .productDetails(productDetails)
                        .fileName(fileName)
                        .fileType(fileType)
                        .build();
                productImageRepository.save(productImage);
            } else if (existingImageIds.contains(imageId)) {
                ProductImage productImage = existingImages.stream()
                        .filter(img -> img.getId().equals(imageId))
                        .findFirst()
                        .orElseThrow(() -> new EshopException(ExceptionConstants.PRODUCT_IMAGE_NOT_FOUND, "Product image not found"));

                productImage.setData(data);
                productImage.setFileName(fileName);
                productImage.setFileType(fileType);
                productImageRepository.save(productImage);
            } else {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Product image ID is invalid");
            }
        }
        for (ProductImage existingImage : existingImages) {
            if (!newImageIds.contains(existingImage.getId())) {
                productImageRepository.delete(existingImage);
            }
        }
    }

    private void addProductVideos(Set<ReqProductVideo> reqProductVideoList, Long productDetailsId) {
        if (productDetailsId == null) {
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Product details id is null");
        }
        ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId, EnumAvailableStatus.ACTIVE.getValue());
        if (productDetails == null) {
            throw new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product details not found");
        }

        for (ReqProductVideo reqProductVideo : reqProductVideoList) {
            byte[] data = reqProductVideo.getData();
            String fileName = reqProductVideo.getFileName();
            String fileType = reqProductVideo.getFileType();
            if (data == null || data.length == 0) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Data is null or empty");
            }
            if (fileName == null || fileName.trim().isEmpty()) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "File name is null or empty");
            }
            if (fileType == null || fileType.trim().isEmpty()) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "File type is null or empty");
            }
            ProductVideo productVideo = ProductVideo.builder()
                    .data(data)
                    .productDetails(productDetails)
                    .fileName(fileName)
                    .fileType(fileType)
                    .build();
            productVideoRepository.save(productVideo);
        }

    }

    private void updateProductVideos(Set<ReqProductVideo> reqProductVideoSet, Long productDetailsId) {
        if (productDetailsId == null) {
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Product details id is null");
        }

        ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(productDetailsId, EnumAvailableStatus.ACTIVE.getValue());
        if (productDetails == null) {
            throw new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product details not found");
        }

        Set<ProductVideo> existingVideos = productVideoRepository.findProductVideoByProductDetailsIdAndActive(productDetailsId, EnumAvailableStatus.ACTIVE.getValue());
        Set<Long> existingVideoIds = existingVideos.stream()
                .map(ProductVideo::getId)
                .collect(Collectors.toSet());
        Set<Long> newVideoIds = reqProductVideoSet.stream()
                .map(ReqProductVideo::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        for (ReqProductVideo reqProductVideo : reqProductVideoSet) {
            byte[] data = reqProductVideo.getData();
            String fileName = reqProductVideo.getFileName();
            String fileType = reqProductVideo.getFileType();
            Long videoId = reqProductVideo.getId();

            if (data == null || data.length == 0) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Data is null or empty");
            }
            if (fileName == null || fileName.trim().isEmpty()) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "File name is null or empty");
            }
            if (fileType == null || fileType.trim().isEmpty()) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "File type is null or empty");
            }

            if (videoId == null) {
                ProductVideo productVideo = ProductVideo.builder()
                        .data(data)
                        .productDetails(productDetails)
                        .fileName(fileName)
                        .fileType(fileType)
                        .build();
                productVideoRepository.save(productVideo);
            } else if (existingVideoIds.contains(videoId)) {
                ProductVideo productVideo = existingVideos.stream()
                        .filter(video -> video.getId().equals(videoId))
                        .findFirst()
                        .orElseThrow(() -> new EshopException(ExceptionConstants.PRODUCT_VIDEO_NOT_FOUND, "Product video not found"));

                productVideo.setData(data);
                productVideo.setFileName(fileName);
                productVideo.setFileType(fileType);
                productVideoRepository.save(productVideo);
            } else {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Product video ID is invalid");
            }
        }

        for (ProductVideo existingVideo : existingVideos) {
            if (!newVideoIds.contains(existingVideo.getId())) {
                productVideoRepository.delete(existingVideo);
            }
        }
    }

    private RespProduct convertToRespProduct(Product product) {
        List<RespProductDetails> respProductDetailsList = product.getProductDetails().stream().map(util::convertToRespProductDetails).toList();
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