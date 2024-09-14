package az.orient.eshop.service;

import az.orient.eshop.dto.request.ReqPayment;
import az.orient.eshop.dto.response.Response;

public interface PaymentService {
    Response payment(ReqPayment reqPayment);
}
