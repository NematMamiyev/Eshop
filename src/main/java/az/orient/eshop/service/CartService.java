package az.orient.eshop.service;

import az.orient.eshop.dto.request.ReqCart;
import az.orient.eshop.dto.response.RespCart;
import az.orient.eshop.dto.response.Response;

public interface CartService {

    Response<RespCart> listByCustomerId(Long customerId);

    Response addCart(ReqCart reqCart);

    Response deleteCart(ReqCart reqCart);
}
