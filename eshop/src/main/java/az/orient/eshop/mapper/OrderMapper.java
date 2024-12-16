package az.orient.eshop.mapper;

import az.orient.eshop.dto.response.RespOrder;
import az.orient.eshop.dto.response.RespProductDetails;
import az.orient.eshop.entity.Order;
import az.orient.eshop.entity.ProductDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",uses = {ProductDetailsMapper.class})
public interface OrderMapper {

   List<RespOrder> toRespOrderList(List<Order> orderList);

   @Mapping(source = "productDetailsList",target = "respProductDetailsList")
   RespOrder toRespOrder(Order order);
}
