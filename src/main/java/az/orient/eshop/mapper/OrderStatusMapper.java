package az.orient.eshop.mapper;

import az.orient.eshop.dto.response.RespOrderStatus;
import az.orient.eshop.entity.OrderStatus;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderStatusMapper {
   List<RespOrderStatus> toRespOrderStatusList(List<OrderStatus> orderStatusList);
}
