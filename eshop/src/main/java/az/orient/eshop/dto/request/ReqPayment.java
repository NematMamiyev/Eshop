package az.orient.eshop.dto.request;

import az.orient.eshop.enums.PaymentMethod;
import lombok.Data;

@Data
public class ReqPayment {
    private PaymentMethod paymentMethod;
    private Long customerId;
}
