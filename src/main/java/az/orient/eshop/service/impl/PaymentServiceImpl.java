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

import java.util.ArrayList;
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

    @Override
    public Response payment(ReqPayment reqPayment) {
        Response response = new Response<>();
        try {
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
            List<ProductDetails> productDetailsListCopy = new ArrayList<>(cart.getProductDetailsList());
            Payment payment = Payment.builder()
                    .paymentMethod(reqPayment.getPaymentMethod())
                    .customer(customer)
                    .amount(cart.getAmount())
                    .build();
            paymentRepository.save(payment);//ödənişi bazaya yazır
            Order order = Order.builder()
                    .customer(customer)
                    .amount(cart.getAmount())
                    .productDetailsList(productDetailsListCopy)
                    .build();
            orderRepository.save(order); //Sifarişi yaradır
            OrderStatus orderStatus = OrderStatus.builder()
                    .status(Status.ORDERED)
                    .order(order)
                    .build();
            orderStatusRepository.save(orderStatus);//Sifarişin statusun yaradır
            emailService.sendSimpleEmail(customer.getEmail(), "Mehsul", Email.ORDERED.getDescription());
            cart.getProductDetailsList().clear();
            cart.setAmount(0F);
            WarehouseWork warehouseWork = WarehouseWork.builder()
                    .order(order)
                    .build();
            warehouseWorkRepository.save(warehouseWork);//Anbara məlumat göndərir
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
}
