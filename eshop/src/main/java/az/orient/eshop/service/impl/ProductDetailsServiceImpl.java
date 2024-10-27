package az.orient.eshop.service.impl;

import az.orient.eshop.dto.request.ReqProductDetails;
import az.orient.eshop.dto.response.RespProductDetails;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.entity.Color;
import az.orient.eshop.entity.Product;
import az.orient.eshop.entity.ProductDetails;
import az.orient.eshop.entity.Size;
import az.orient.eshop.enums.Currency;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.repository.ColorRepository;
import az.orient.eshop.repository.ProductDetailsRepository;
import az.orient.eshop.repository.ProductRepository;
import az.orient.eshop.repository.SizeRepository;
import az.orient.eshop.service.ProductDetailsService;
import az.orient.eshop.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDetailsServiceImpl implements ProductDetailsService {

    private final ProductDetailsRepository productDetailsRepository;
    private final ColorRepository colorRepository;
    private final ProductRepository productRepository;
    private final SizeRepository sizeRepository;
    private final Utility utility = new Utility();

    @Override
    public Response<RespProductDetails> addProductDetails(Long productId, ReqProductDetails reqProductDetails) {
        Response<RespProductDetails> response = new Response<>();
        try {
            if (productId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id is null");
            }
            Product product = productRepository.findProductByIdAndActive(productId, EnumAvailableStatus.ACTIVE.getValue());
            if (product == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found");
            }
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
            RespProductDetails respProductDetails = utility.convertToRespProductDetails(productDetails);
            response.setT(respProductDetails);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (
                EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response<RespProductDetails> updateProductDetails(Long id, ReqProductDetails reqProductDetails) {
        Response<RespProductDetails> response = new Response<>();
        try {
            if (id == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id is null");
            }
            ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
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
            Product product = productRepository.findProductByIdAndActive(reqProductDetails.getProductId(), EnumAvailableStatus.ACTIVE.getValue());
            productDetails.setProduct(product);
            productDetails.setPrice(price);
            productDetails.setCurrency(currency);
            productDetails.setSize(size);
            productDetails.setStock(stock);
            productDetails.setColor(color);
            productDetailsRepository.save(productDetails);
            RespProductDetails respProductDetails = utility.convertToRespProductDetails(productDetails);
            response.setT(respProductDetails);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (
                EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response<List<RespProductDetails>> getProductDetails() {
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
    public Response<RespProductDetails> getProductDetailsById(Long id) {
        Response<RespProductDetails> response = new Response<>();
        try {
            if (id == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Product details id is null");
            }
            ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (productDetails == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_DETAILS_NOT_FOUND, "Product details not found");
            }
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
    public Response deleteProductDetails(Long id) {
        Response response = new Response<>();
        try {
            if (id == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Product details id is null");
            }
            ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            if (productDetails == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_DETAILS_NOT_FOUND, "Product details not found");
            }
            productDetails.setActive(EnumAvailableStatus.DEACTIVE.getValue());
            productDetailsRepository.save(productDetails);
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

}
