package az.orient.eshop.service.impl;

import az.orient.eshop.dto.response.*;
import az.orient.eshop.entity.Customer;
import az.orient.eshop.entity.Order;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.repository.CustomerRepository;
import az.orient.eshop.repository.OrderRepository;
import az.orient.eshop.service.OrderService;
import az.orient.eshop.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final Utility util = new Utility();

    @Override
    public Response<List<RespOrder>> getList(Long customerId) {
        Response<List<RespOrder>> response = new Response<>();
        try {
            if (customerId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Id not found");
            }
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new EshopException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            List<Order> orders = orderRepository.findOrderByCustomerIdAndActive(customerId, EnumAvailableStatus.ACTIVE.getValue());
            List<RespOrder> respOrders = orders.stream().map(this::convert).toList();
            response.setT(respOrders);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (EshopException ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }
    private RespOrder convert(Order order){
        List<RespProductDetails> respProductDetailsList = order.getProductDetailsList().stream().map(util::convertToRespProductDetails).toList();
        return RespOrder.builder()
                .id(order.getId())
                .amount(order.getAmount())
                .respProductDetailsList(respProductDetailsList)
                .build();
    }
}