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
import az.orient.eshop.mapper.ProductDetailsMapper;
import az.orient.eshop.repository.ColorRepository;
import az.orient.eshop.repository.ProductDetailsRepository;
import az.orient.eshop.repository.ProductRepository;
import az.orient.eshop.repository.SizeRepository;
import az.orient.eshop.service.ProductDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDetailsServiceImpl implements ProductDetailsService {

    private final ProductDetailsRepository productDetailsRepository;
    private final ColorRepository colorRepository;
    private final ProductRepository productRepository;
    private final SizeRepository sizeRepository;
    private final ProductDetailsMapper productDetailsMapper;

    @Override
    public Response<RespProductDetails> addProductDetails(ReqProductDetails reqProductDetails) {
        Response<RespProductDetails> response = new Response<>();
        try {

            Long sizeId = reqProductDetails.getSizeId();
            Long colorId = reqProductDetails.getColorId();
            Integer stock = reqProductDetails.getStock();
            Currency.fromValue(reqProductDetails.getCurrency().getValue());
            BigDecimal price = reqProductDetails.getPrice();
            Long productId = reqProductDetails.getProductId();
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
            if (productId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id is null");
            }
            Product product = productRepository.findProductByIdAndActive(productId, EnumAvailableStatus.ACTIVE.getValue());
            if (product == null) {
                throw new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found");
            }
            ProductDetails productDetails = productDetailsMapper.toProductDetails(reqProductDetails);
            productDetailsRepository.save(productDetails);
            RespProductDetails respProductDetails = productDetailsMapper.toRespProductDetails(productDetails);
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
            Currency.fromValue(reqProductDetails.getCurrency().getValue());
            BigDecimal price = reqProductDetails.getPrice();
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
            productDetailsMapper.updateProductDetailsFromReqProductDetails(productDetails,reqProductDetails);
            productDetailsRepository.save(productDetails);
            RespProductDetails respProductDetails = productDetailsMapper.toRespProductDetails(productDetails);
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
            List<RespProductDetails> respProductDetailsList =productDetailsMapper.toRespProductDetailsList(productDetailsList);
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
            RespProductDetails respProductDetails = productDetailsMapper.toRespProductDetails(productDetails);
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
