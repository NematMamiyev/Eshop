package az.orient.eshop.service;

import az.orient.eshop.dto.request.ReqPayment;
import az.orient.eshop.dto.response.RespStatus;

public interface PaymentService {
    RespStatus payment(ReqPayment reqPayment);
}
