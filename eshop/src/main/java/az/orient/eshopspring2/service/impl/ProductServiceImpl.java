package az.orient.eshopspring2.service.impl;

import az.orient.eshopspring2.dto.request.ReqProduct;
import az.orient.eshopspring2.dto.response.*;
import az.orient.eshopspring2.entity.*;
import az.orient.eshopspring2.enums.EnumAvailableStatus;
import az.orient.eshopspring2.enums.Gender;
import az.orient.eshopspring2.exception.EshopException;
import az.orient.eshopspring2.exception.ExceptionConstants;
import az.orient.eshopspring2.repository.*;
import az.orient.eshopspring2.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;
    private final SubcategoryRepository subcategoryRepository;

    @Override
    public Response<RespProduct> addProduct(ReqProduct reqProduct) {
        Response<RespProduct> response = new Response<>();
        try {
            String name = reqProduct.getName();
            Float price = reqProduct.getPrice();
            Gender gender = reqProduct.getGender();
            String productNumber = reqProduct.getProductNumber();
            Long brandId = reqProduct.getBrandId();
            Long sizeId = reqProduct.getSizeId();
            Long colorId = reqProduct.getColorId();
            Long subcategoryId = reqProduct.getSubcategoryId();
            Integer count = reqProduct.getCount();
            if (productNumber == null || name == null || price == null || gender == null || brandId == null || sizeId == null || colorId == null || subcategoryId == null || count == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request dataa");
            }
            Brand brand = brandRepository.findByIdAndActive(brandId, EnumAvailableStatus.ACTIVE.getValue());
            if (brand == null) {
                throw new EshopException(ExceptionConstants.BRAND_NOT_FOUND, "Brand not found");
            }
            Size size = sizeRepository.findSizeByIdAndActive(sizeId, EnumAvailableStatus.ACTIVE.getValue());
            if (size == null) {
                throw new EshopException(ExceptionConstants.SIZE_NOT_FOUND, "Size not found");
            }
            Color color = colorRepository.findByIdAndActive(colorId, EnumAvailableStatus.ACTIVE.getValue());
            if (color == null) {
                throw new EshopException(ExceptionConstants.COLOR_NOT_FOUND, "Color not found");
            }
            Subcategory subcategory = subcategoryRepository.findSubcategoryByIdAndActive(subcategoryId, EnumAvailableStatus.ACTIVE.getValue());
            if (subcategory == null) {
                throw new EshopException(ExceptionConstants.SUBCATEGORY_NOT_FOUND, "Subcategory not found");
            }
            Product product = Product.builder()
                    .name(name)
                    .productInformation(reqProduct.getProductInformation())
                    .image(reqProduct.getImage())
                    .video(reqProduct.getVideo())
                    .subcategory(subcategory)
                    .productNumber(productNumber)
                    .expertionDate(reqProduct.getExpertionDate())
                    .color(color)
                    .brand(brand)
                    .gender(reqProduct.getGender())
                    .size(size)
                    .count(count)
                    .price(price)
                    .build();
            productRepository.save(product);
            RespProduct respProduct = convert(product);
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
    public Response<List<RespProduct>> getProductList() {
        Response<List<RespProduct>> response = new Response<>();
        try {
            List<Product> productList = productRepository.findAllByActive(EnumAvailableStatus.ACTIVE.getValue());
            if (productList.isEmpty()) {
                throw new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product is empty");
            }
            List<RespProduct> respProductList = productList.stream().map(this::convert).toList();
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
            RespProduct respProduct = convert(product);
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
    public Response<RespProduct> updateProduct(ReqProduct reqProduct) {
        Response<RespProduct> response = new Response<>();
        try {
            Long id = reqProduct.getId();
            String name = reqProduct.getName();
            Float price = reqProduct.getPrice();
            Gender gender = reqProduct.getGender();
            String productNumber = reqProduct.getProductNumber();
            Long brandId = reqProduct.getBrandId();
            Long sizeId = reqProduct.getSizeId();
            Long colorId = reqProduct.getColorId();
            Long subcategoryId = reqProduct.getSubcategoryId();
            Integer count = reqProduct.getCount();
            if (id == null || productNumber == null || name == null || price == null || gender == null || brandId == null || sizeId == null || colorId == null || subcategoryId == null || count == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request dataa");
            }
            Product product = productRepository.findProductByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (product == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found");
            }
            Brand brand = brandRepository.findByIdAndActive(brandId, EnumAvailableStatus.ACTIVE.getValue());
            if (brand == null) {
                throw new EshopException(ExceptionConstants.BRAND_NOT_FOUND, "Brand not found");
            }
            Size size = sizeRepository.findSizeByIdAndActive(sizeId, EnumAvailableStatus.ACTIVE.getValue());
            if (size == null) {
                throw new EshopException(ExceptionConstants.SIZE_NOT_FOUND, "Size not found");
            }
            Color color = colorRepository.findByIdAndActive(colorId, EnumAvailableStatus.ACTIVE.getValue());
            if (color == null) {
                throw new EshopException(ExceptionConstants.COLOR_NOT_FOUND, "Color not found");
            }
            Subcategory subcategory = subcategoryRepository.findSubcategoryByIdAndActive(subcategoryId, EnumAvailableStatus.ACTIVE.getValue());
            if (subcategory == null) {
                throw new EshopException(ExceptionConstants.SUBCATEGORY_NOT_FOUND, "Subcategory not found");
            }
            product.setName(name);
            product.setPrice(price);
            product.setProductInformation(reqProduct.getProductInformation());
            product.setExpertionDate(reqProduct.getExpertionDate());
            product.setGender(gender);
            product.setProductNumber(productNumber);
            product.setImage(reqProduct.getImage());
            product.setVideo(reqProduct.getVideo());
            product.setBrand(brand);
            product.setSize(size);
            product.setColor(color);
            product.setSubcategory(subcategory);
            product.setCount(count);
            productRepository.save(product);
            RespProduct respProduct = convert(product);
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

    private RespProduct convert(Product product) {
        RespCategory respCategory = RespCategory.builder()
                .id(product.getSubcategory().getCategory().getId())
                .name(product.getSubcategory().getCategory().getName())
                .build();
        RespSubcategory respSubcategory = RespSubcategory.builder()
                .id(product.getSubcategory().getId())
                .name(product.getSubcategory().getName())
                .respCategory(respCategory)
                .build();
        RespColor respColor = RespColor.builder()
                .id(product.getColor().getId())
                .name(product.getColor().getName())
                .build();
        RespBrand respBrand = RespBrand.builder()
                .id(product.getBrand().getId())
                .name(product.getBrand().getName())
                .build();
        RespSize respSize = RespSize.builder()
                .id(product.getSize().getId())
                .name(product.getSize().getName())
                .build();
        return RespProduct.builder()
                .id(product.getId())
                .name(product.getName())
                .productNumber(product.getProductNumber())
                .productInformation(product.getProductInformation())
                .image(product.getImage())
                .video(product.getVideo())
                .respSubcategory(respSubcategory)
                .expertionDate(product.getExpertionDate())
                .respColor(respColor)
                .respBrand(respBrand)
                .gender(product.getGender())
                .respSize(respSize)
                .count(product.getCount())
                .price(product.getPrice())
                .build();
    }
}