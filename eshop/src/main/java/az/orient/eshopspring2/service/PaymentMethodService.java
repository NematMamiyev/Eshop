package az.orient.eshopspring2.service;

import az.orient.eshopspring2.dto.request.ReqPaymentMethod;
import az.orient.eshopspring2.dto.response.RespPaymentMethod;
import az.orient.eshopspring2.dto.response.Response;

import java.util.List;

public interface PaymentMethodService {
    Response<RespPaymentMethod> addPaymentMethod(ReqPaymentMethod reqPaymentMethod);

    Response<List<RespPaymentMethod>> getPaymentMethodList();

    Response<RespPaymentMethod> getPaymentMethodById(Long id);

    Response<RespPaymentMethod> updatePaymentMethod(ReqPaymentMethod reqPaymentMethod);

    Response deletePaymentMethod(Long id);
}
