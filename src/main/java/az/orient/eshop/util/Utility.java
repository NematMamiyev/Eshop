package az.orient.eshop.util;

import az.orient.eshop.dto.response.*;
import az.orient.eshop.entity.ProductDetails;


public class Utility {
    public RespProductDetails convertToRespProductDetails(ProductDetails productDetails) {
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
