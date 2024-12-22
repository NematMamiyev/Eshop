package az.orient.eshop.service.impl;

import az.orient.eshop.dto.response.*;
import az.orient.eshop.entity.Order;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.mapper.OrderMapper;
import az.orient.eshop.repository.OrderRepository;
import az.orient.eshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public Response<List<RespOrder>> getList(Long customerId) {
        Response<List<RespOrder>> response = new Response<>();
            List<Order> orders = orderRepository.findOrderByCustomerIdAndActive(customerId, EnumAvailableStatus.ACTIVE.getValue());
            response.setT(orderMapper.toRespOrderList(orders));
            response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }
}
