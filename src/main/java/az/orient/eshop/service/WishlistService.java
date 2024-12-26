package az.orient.eshop.service;

import az.orient.eshop.dto.response.RespProductDetails;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface WishlistService {
    RespStatus addWishlist(Long productDetailsId, HttpServletRequest httpServletRequest);

    RespStatus deleteWishlist(Long productDetailsId, HttpServletRequest httpServletRequest);

    Response<List<RespProductDetails>> listByCustomerId(HttpServletRequest httpServletRequest);
}
