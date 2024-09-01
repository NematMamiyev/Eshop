package az.orient.eshop.service;

import az.orient.eshop.dto.response.RespCart;
import az.orient.eshop.dto.response.Response;

import java.util.List;

public interface CartService {
    Response<List<RespCart>> getCartList();

    Response<List<RespCart>> listByCustomerId(Long customerId);

    Response addCart(Long productId, Long customerId);

    Response deleteCart(Long productId, Long customerId);
}
