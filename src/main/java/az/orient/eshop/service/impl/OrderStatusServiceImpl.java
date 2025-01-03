package az.orient.eshop.service.impl;

import az.orient.eshop.dto.response.RespOrderStatus;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.entity.Order;
import az.orient.eshop.entity.OrderStatus;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.mapper.OrderStatusMapper;
import az.orient.eshop.repository.OrderRepository;
import az.orient.eshop.repository.OrderStatusRepository;
import az.orient.eshop.service.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {

    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderStatusMapper orderStatusMapper;

    @Override
    public Response<List<RespOrderStatus>> getOrderStatusList(Long orderId) {
        Response<List<RespOrderStatus>> response = new Response<>();
        Order order = orderRepository.findOrderByIdAndActive(orderId, EnumAvailableStatus.ACTIVE.getValue());
        if (order == null) {
            throw new EshopException(ExceptionConstants.ORDER_NOT_FOUND, "Order not found");
        }
        List<OrderStatus> orderStatusList = orderStatusRepository.findOrderStatusByOrderIdAndActive(orderId, EnumAvailableStatus.ACTIVE.getValue());
        response.setT(orderStatusMapper.toRespOrderStatusList(orderStatusList));
        response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }
}
