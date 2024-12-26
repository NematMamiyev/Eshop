package az.orient.eshop.service;

import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.enums.PaymentMethod;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {
    RespStatus payment(PaymentMethod paymentMethod, HttpServletRequest httpServletRequest);
}
