package az.orient.eshop.service;

import az.orient.eshop.dto.request.ReqWishlist;
import az.orient.eshop.dto.response.RespProductDetails;
import az.orient.eshop.dto.response.RespWishlist;
import az.orient.eshop.dto.response.Response;

import java.util.List;

public interface WishlistService {
    Response addWishlist(ReqWishlist reqWishlist);

    Response deleteWishlist(ReqWishlist reqWishlist);

    Response<List<RespProductDetails>> listByCustomerId(Long customerId);
}
