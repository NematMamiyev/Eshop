package az.orient.eshop.dto.request;

import az.orient.eshop.enums.PaymentMethod;
import az.orient.eshop.validation.CustomerActive;
import az.orient.eshop.validation.ValidPaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReqPayment {
    @NotNull(message = "Payment method is required")
    @ValidPaymentMethod
    private PaymentMethod paymentMethod;
    @NotNull(message = "Customer id is required")
    @CustomerActive
    private Long customerId;
}
