package az.orient.eshop.service.impl;

import az.orient.eshop.dto.request.ReqPayment;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.entity.*;
import az.orient.eshop.enums.Email;
import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.enums.PaymentMethod;
import az.orient.eshop.enums.Status;
import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import az.orient.eshop.repository.*;
import az.orient.eshop.service.PaymentService;
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

    @Override
    public Response payment(ReqPayment reqPayment) {
        Response response = new Response<>();
            Long customerId = reqPayment.getCustomerId();
            if (customerId == null) {
                throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
            }
            PaymentMethod.fromValue(reqPayment.getPaymentMethod().getValue());
            Customer customer = customerRepository.findCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.getValue());
            if (customer == null) {
                throw new EshopException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found");
            }
            Cart cart = cartRepository.findCartByCustomerIdAndActive(customerId, EnumAvailableStatus.ACTIVE.getValue());
            if (cart == null) {
                throw new EshopException(ExceptionConstants.CART_NOT_FOUND, "Cart is empty");
            }
            if (cart.getProductDetailsList().isEmpty()){
                throw new EshopException(ExceptionConstants.CART_IS_EMPTY,"Cart is empty");
            }
            List<ProductDetails> productDetailsList = cart.getProductDetailsList();
            for (ProductDetails productDetails : productDetailsList) {
                int currentStock = productDetails.getStock();
                if (currentStock == 0) {
                    throw new EshopException(ExceptionConstants.OUT_OF_STOCK,"Out of stock");
                }else {
                    productDetails.setStock(currentStock - 1);
                    if (productDetails.getStock() == 0){
                        productDetails.setActive(EnumAvailableStatus.DEACTIVE.getValue());
                    }
                    productDetailsRepository.save(productDetails);
                }
            }
            Payment payment = Payment.builder()
                    .paymentMethod(reqPayment.getPaymentMethod())
                    .customer(customer)
                    .amount(cart.getAmount())
                    .build();
            paymentRepository.save(payment);
            Order order = Order.builder()
                    .customer(customer)
                    .amount(cart.getAmount())
                    .productDetailsList(productDetailsList)
                    .build();
            orderRepository.save(order);
            OrderStatus orderStatus = OrderStatus.builder()
                    .status(Status.ORDERED)
                    .order(order)
                    .build();
            orderStatusRepository.save(orderStatus);
            emailService.sendSimpleEmail(customer.getEmail(), "Mehsul", Email.ORDERED.getDescription());
            cart.getProductDetailsList().clear();
            cart.setAmount(BigDecimal.ZERO);
            WarehouseWork warehouseWork = WarehouseWork.builder()
                    .order(order)
                    .build();
            warehouseWorkRepository.save(warehouseWork);
            response.setStatus(RespStatus.getSuccessMessage());
        return response;
    }
}
