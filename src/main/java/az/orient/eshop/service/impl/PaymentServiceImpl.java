package az.orient.eshop.service.impl;

import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.entity.*;
import az.orient.eshop.enums.Email;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.enums.PaymentMethod;
import az.orient.eshop.enums.Status;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.repository.*;
import az.orient.eshop.security.JwtGenerator;
import az.orient.eshop.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final WarehouseWorkRepository warehouseWorkRepository;
    private final EmailServiceImpl emailService;
    private final ProductDetailsRepository productDetailsRepository;
    private final JwtGenerator jwtGenerator;

    @Override
    public RespStatus payment(PaymentMethod paymentMethod, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Long customerId = jwtGenerator.getId(token);
        Cart cart = cartRepository.findCartByCustomerIdAndActive(customerId, EnumAvailableStatus.ACTIVE.getValue());
        if (cart == null) {
            throw new EshopException(ExceptionConstants.CART_NOT_FOUND, "Cart not found");
        }
        if (cart.getProductDetailsList().isEmpty()) {
            throw new EshopException(ExceptionConstants.CART_IS_EMPTY, "Cart is empty");
        }
        List<ProductDetails> productDetailsList = cart.getProductDetailsList();
        for (ProductDetails productDetails : productDetailsList) {
            int currentStock = productDetails.getStock();
            if (currentStock == 0) {
                throw new EshopException(ExceptionConstants.OUT_OF_STOCK, "Out of stock");
            } else {
                productDetails.setStock(currentStock - 1);
                if (productDetails.getStock() == 0) {
                    productDetails.setActive(EnumAvailableStatus.DEACTIVATED.getValue());
                }
                productDetailsRepository.save(productDetails);
            }
        }
        Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.getValue());
        Payment payment = Payment.builder()
                .paymentMethod(paymentMethod)
                .customer(customer)
                .amount(cart.getAmount())
                .build();
        paymentRepository.save(payment);
        Order order = Order.builder()
                .customer(customer)
                .amount(cart.getAmount())
                .build();
        orderRepository.save(order);
        for (ProductDetails productDetails : productDetailsList) {
            order.getProductDetailsList().add(productDetails);
        }
        OrderStatus orderStatus = OrderStatus.builder()
                .status(Status.ORDERED)
                .order(order)
                .build();
        orderStatusRepository.save(orderStatus);
       // emailService.sendSimpleEmail(customer.getEmail(), "Mehsul", Email.ORDERED.getDescription());
        cart.getProductDetailsList().clear();
        cart.setAmount(BigDecimal.ZERO);
        WarehouseWork warehouseWork = WarehouseWork.builder()
                .order(order)
                .build();
        warehouseWorkRepository.save(warehouseWork);
        return RespStatus.getSuccessMessage();
    }
}
