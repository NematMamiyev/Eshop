package az.orient.eshop.mapper;

import az.orient.eshop.dto.request.ReqWarehouse;
import az.orient.eshop.dto.response.RespWarehouse;
import az.orient.eshop.entity.Warehouse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {

    Warehouse toWarehouse(ReqWarehouse reqWarehouse);
    RespWarehouse toRespWarehouse(Warehouse warehouse);
    List<RespWarehouse> toRespWarehouseList(List<Warehouse> warehouseList);
    void updateWarehouseFromReqWarehouse(@MappingTarget Warehouse warehouse, ReqWarehouse reqWarehouse);
}