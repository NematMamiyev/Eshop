package az.orient.eshop.service.impl;

import az.orient.eshop.dto.response.RespOrder;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.entity.Order;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.mapper.OrderMapper;
import az.orient.eshop.repository.OrderRepository;
import az.orient.eshop.security.JwtGenerator;
import az.orient.eshop.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final JwtGenerator jwtGenerator;

    @Override
    public Response<List<RespOrder>> getList(HttpServletRequest httpServletRequest) {
        Response<List<RespOrder>> response = new Response<>();
        String token = httpServletRequest.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        List<Order> orders = orderRepository.findOrderByCustomerIdAndActive(jwtGenerator.getId(token), EnumAvailableStatus.ACTIVE.getValue());
        response.setT(orderMapper.toRespOrderList(orders));
        response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }
}
