package az.orient.eshop.service;

import az.orient.eshop.dto.request.ReqWishlist;
import az.orient.eshop.dto.response.RespProductDetails;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;

import java.util.List;

public interface WishlistService {
    RespStatus addWishlist(ReqWishlist reqWishlist);

    RespStatus deleteWishlist(ReqWishlist reqWishlist);

    Response<List<RespProductDetails>> listByCustomerId(Long customerId);
}
