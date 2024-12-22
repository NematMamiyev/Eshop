package az.orient.eshop.mapper;

import az.orient.eshop.dto.request.ReqProduct;
import az.orient.eshop.dto.response.RespProduct;
import az.orient.eshop.dto.response.RespProductDetailsForProduct;
import az.orient.eshop.entity.Product;
import az.orient.eshop.entity.ProductDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper(componentModel = "spring",uses = {MapperHelper.class})
public interface ProductMapper {
    @Mapping(source = "subcategoryId", target = "subcategory", qualifiedByName = "mapSubcategory")
    @Mapping(source = "brandId", target = "brand", qualifiedByName = "mapBrand")
    Product toProduct(ReqProduct reqProduct);

    @Mapping(source = "size", target = "respSize")
    @Mapping(source = "color", target = "respColor")
    RespProductDetailsForProduct toRespProductDetailsForProduct(ProductDetails productDetails);

    @Mapping(source = "subcategory", target = "respSubcategory")
    @Mapping(source = "brand", target = "respBrand")
    @Mapping(source = "subcategory.category", target = "respSubcategory.respCategory")
    @Mapping(source = "productDetailsList", target = "respProductDetailsForProductList")
    RespProduct toRespProduct(Product product);

    List<RespProduct> toRespProductList(List<Product> productList);

    @Mapping(source = "subcategoryId", target = "subcategory", qualifiedByName = "mapSubcategory")
    @Mapping(source = "brandId", target = "brand", qualifiedByName = "mapBrand")
    void updateProductFromReqProduct(@MappingTarget Product product,ReqProduct reqProduct);


}
