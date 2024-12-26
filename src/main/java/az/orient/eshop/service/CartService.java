package az.orient.eshop.service;

import az.orient.eshop.dto.response.RespCart;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import jakarta.servlet.http.HttpServletRequest;

public interface CartService {

    Response<RespCart> listByCustomerId(HttpServletRequest httpServletRequest);

    RespStatus addCart(Long productDetailsId, HttpServletRequest httpServletRequest);

    RespStatus deleteCart(Long productDetailsId, HttpServletRequest httpServletRequest);
}
