package az.orient.eshop.mapper;

import az.orient.eshop.dto.request.ReqShelfProductDetails;
import az.orient.eshop.dto.response.RespShelfProductDetails;
import az.orient.eshop.entity.ShelfProductDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MapperHelper.class, ProductDetailsMapper.class})
public interface ShelfProductDetailsMapper {

    @Mapping(source = "shelfId",target = "shelf",qualifiedByName = "mapShelf")
    @Mapping(source = "productDetailsId",target = "productDetails",qualifiedByName = "mapProductDetails")
    ShelfProductDetails toShelfProductDetails(ReqShelfProductDetails reqShelfProductDetails);

    @Mapping(source = "shelf",target = "respShelf")
    @Mapping(source = "productDetails",target = "respProductDetails")
    RespShelfProductDetails toRespShelfProductDetails(ShelfProductDetails shelfProductDetails);
}
