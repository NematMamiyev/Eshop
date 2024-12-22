package az.orient.eshop.service;

import az.orient.eshop.dto.request.ReqCart;
import az.orient.eshop.dto.response.RespCart;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;

public interface CartService {

    Response<RespCart> listByCustomerId(Long customerId);

    RespStatus addCart(ReqCart reqCart);

    RespStatus deleteCart(ReqCart reqCart);
}
