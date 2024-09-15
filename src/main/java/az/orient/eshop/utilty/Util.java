package az.orient.eshop.utilty;

import az.orient.eshop.dto.response.*;
import az.orient.eshop.entity.ProductDetails;
import az.orient.eshop.entity.ProductImage;
import az.orient.eshop.entity.ProductVideo;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Util {
    public RespProductImage convertToRespProductImage(ProductImage productImage) {
        return RespProductImage.builder()
                .data(productImage.getData())
                .fileName(productImage.getFileName())
                .fileType(productImage.getFileType())
                .build();
    }

    public RespProductVideo convertToRespProductVideo(ProductVideo productVideo) {
        return RespProductVideo.builder()
                .data(productVideo.getData())
                .fileName(productVideo.getFileName())
                .fileType(productVideo.getFileType())
                .build();
    }
    public RespProductDetails convertToRespProductDetails(ProductDetails productDetails) {
        Set<RespProductImage> respProductImages = Optional.ofNullable(productDetails.getImages())
                .orElse(Collections.emptySet())
                .stream()
                .map(this::convertToRespProductImage)
                .collect(Collectors.toSet());
        Set<RespProductVideo> respProductVideos = Optional.ofNullable(productDetails.getVideos())
                .orElse(Collections.emptySet())
                .stream()
                .map(this::convertToRespProductVideo)
                .collect(Collectors.toSet());
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
                .respProductVideoList(respProductVideos)
                .respProductImageList(respProductImages)
                .build();
    }
}
