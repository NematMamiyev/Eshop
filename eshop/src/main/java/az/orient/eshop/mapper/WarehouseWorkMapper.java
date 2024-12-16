package az.orient.eshop.mapper;

import az.orient.eshop.dto.response.RespWareHouseWork;
import az.orient.eshop.entity.WarehouseWork;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",uses = {ProductDetailsMapper.class})
public interface WarehouseWorkMapper {

    @Mapping(source = "order",target = "respOrder")
    @Mapping(source = "order.productDetailsList",target = "respOrder.respProductDetailsList")
    RespWareHouseWork toRespWarehouseWork(WarehouseWork warehouseWork);

    List<RespWareHouseWork> toRespWarehouseWorkList(List<WarehouseWork> warehouseWorkList);
}
