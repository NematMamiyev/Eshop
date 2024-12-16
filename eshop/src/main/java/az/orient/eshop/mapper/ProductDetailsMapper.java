package az.orient.eshop.mapper;

import az.orient.eshop.dto.request.ReqProductDetails;
import az.orient.eshop.dto.response.RespProductDetails;
import az.orient.eshop.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


import java.util.List;

@Mapper(componentModel = "spring",uses = {MapperHelper.class})
public interface ProductDetailsMapper {
    @Mapping(source = "productId", target = "product", qualifiedByName = "mapProduct")
    @Mapping(source = "sizeId", target = "size", qualifiedByName = "mapSize")
    @Mapping(source = "colorId", target = "color", qualifiedByName = "mapColor")
    ProductDetails toProductDetails(ReqProductDetails reqProductDetails);

    @Mapping(source = "product", target = "respProductForProductDetails")
    @Mapping(source = "size", target = "respSize")
    @Mapping(source = "color", target = "respColor")
    @Mapping(source = "product.brand", target = "respProductForProductDetails.respBrand")
    @Mapping(source = "product.subcategory", target = "respProductForProductDetails.respSubcategory")
    @Mapping(source = "product.subcategory.category", target = "respProductForProductDetails.respSubcategory.respCategory")
    RespProductDetails toRespProductDetails(ProductDetails productDetails);

    List<RespProductDetails> toRespProductDetailsList(List<ProductDetails> productDetailsList);

    @Mapping(source = "productId", target = "product", qualifiedByName = "mapProduct")
    @Mapping(source = "sizeId", target = "size", qualifiedByName = "mapSize")
    @Mapping(source = "colorId", target = "color", qualifiedByName = "mapColor")
    void updateProductDetailsFromReqProductDetails(@MappingTarget ProductDetails productDetails, ReqProductDetails reqProductDetails);


}
