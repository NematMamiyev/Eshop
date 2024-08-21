package az.orient.eshop.service;

import az.orient.eshop.dto.request.ReqPaymentMethod;
import az.orient.eshop.dto.response.RespPaymentMethod;
import az.orient.eshop.dto.response.Response;

import java.util.List;

public interface PaymentMethodService {
    Response<RespPaymentMethod> addPaymentMethod(ReqPaymentMethod reqPaymentMethod);

    Response<List<RespPaymentMethod>> getPaymentMethodList();

    Response<RespPaymentMethod> getPaymentMethodById(Long id);

    Response<RespPaymentMethod> updatePaymentMethod(ReqPaymentMethod reqPaymentMethod);

    Response deletePaymentMethod(Long id);
}
