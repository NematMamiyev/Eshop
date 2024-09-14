/*
package az.orient.eshop.service.impl;

import az.orient.eshop.dto.request.ReqProductDetails;
import az.orient.eshop.dto.response.*;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDetailsServiceImpl implements ProductDetailsService {
    private final ProductDetailsRepository productDetailsRepository;
    private final ProductRepository productRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;

    public Response<List<RespProductDetails>> addProductDetails(List<ReqProductDetails> reqProductDetailsList) {
        Response<List<RespProductDetails>> response = new Response<>();
        try {
            if (reqProductDetailsList == null || reqProductDetailsList.isEmpty()) {
                throw new EshopException(ExceptionConstants.PRODUCT_DETAILS_NOT_FOUND, "Product details is empty");
            }
            List<RespProductDetails> respProductDetailsList = new ArrayList<>();
            List<ProductDetails> productDetailsToSave = new ArrayList<>(); // toplu saxlamaq üçün siyahı
            for (ReqProductDetails reqProductDetails : reqProductDetailsList) {
                Long productId = reqProductDetails.getProductId();
                Long sizeId = reqProductDetails.getSizeId();
                Long colorId = reqProductDetails.getColorId();
                Integer stock = reqProductDetails.getStock();
                Float price = reqProductDetails.getPrice();
                if (productId == null || sizeId == null || stock == null || colorId == null || price == null) {
                    throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
                }
                Product product = productRepository.findProductByIdAndActive(productId, EnumAvailableStatus.ACTIVE.getValue());
                Size size = sizeRepository.findSizeByIdAndActive(sizeId, EnumAvailableStatus.ACTIVE.getValue());
                Color color = colorRepository.findByIdAndActive(colorId, EnumAvailableStatus.ACTIVE.getValue());
                if (product == null) {
                    throw new EshopException(ExceptionConstants.PRODUCT_NOT_FOUND, "Product not found");
                }
                if (size == null) {
                    throw new EshopException(ExceptionConstants.SIZE_NOT_FOUND, "Size not found");
                }
                if (color == null) {
                    throw new EshopException(ExceptionConstants.COLOR_NOT_FOUND, "Color not found");
                }
                ProductDetails productDetails = ProductDetails.builder()
                        .product(product)
                        .price(price)
                        .currency(reqProductDetails.getCurrency())
                        .size(size)
                        .stock(stock)
                        .color(color)
                        .build();

                productDetailsToSave.add(productDetails);
                RespProductDetails respProductDetails = convert(productDetails);
                respProductDetailsList.add(respProductDetails);
            }
            productDetailsRepository.saveAll(productDetailsToSave);
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
    public Response<List<RespProductDetails>> getProductDetailsList() {
        return null;
    }

    @Override
    public Response<RespProductDetails> getProductDetailsById(Long id) {
        Response<RespProductDetails> response = new Response<>();
        try {
            if (id == null){
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Product details id is null");
            }
            ProductDetails productDetails = productDetailsRepository.findProductDetailsByIdAndActive(id, EnumAvailableStatus.ACTIVE.getValue());
            RespProductDetails respProductDetails = convert(productDetails);
            response.setT(respProductDetails);
            response.setStatus(RespStatus.getSuccessMessage());
        }catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    @Override
    public Response<RespProductDetails> updateProductDetails(ReqProductDetails reqProductDetails) {
        return null;
    }

    @Override
    public Response deleteProductDetails(Long id) {
        return null;
    }
    private RespProductDetails convert(ProductDetails productDetails) {
        RespSize respSize = RespSize.builder()
                .id(productDetails.getSize().getId())
                .name(productDetails.getSize().getName())
                .build();
        RespColor respColor = RespColor.builder()
                .id(productDetails.getColor().getId())
                .name(productDetails.getColor().getName())
                .build();
        return RespProductDetails.builder()
                .id(productDetails.getId())
                .respSize(respSize)
                .respColor(respColor)
                .currency(productDetails.getCurrency())
                .price(productDetails.getPrice())
                .stock(productDetails.getStock())
                .build();
    }
}
*/
