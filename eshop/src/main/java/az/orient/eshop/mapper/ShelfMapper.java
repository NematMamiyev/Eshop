package az.orient.eshop.mapper;

import az.orient.eshop.dto.request.ReqShelf;
import az.orient.eshop.dto.response.RespShelf;
import az.orient.eshop.entity.Shelf;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring",uses = {MapperHelper.class})
public interface ShelfMapper {

    @Mapping(source = "warehouseId",target = "warehouse",qualifiedByName = "mapWarehouse")
    Shelf toShelf(ReqShelf reqShelf);

    @Mapping(source = "warehouse",target = "respWarehouse")
    RespShelf toRespShelf(Shelf shelf);

    List<RespShelf> toRespShelfList(List<Shelf> shelfList);

    @Mapping(source = "warehouseId",target = "warehouse",qualifiedByName = "mapWarehouse")
    void updateShelfFromReqShelf(@MappingTarget Shelf shelf,ReqShelf reqShelf);
}
