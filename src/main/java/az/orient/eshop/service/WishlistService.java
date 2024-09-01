package az.orient.eshop.service;

import az.orient.eshop.dto.response.RespWishlist;
import az.orient.eshop.dto.response.Response;

import java.util.List;

public interface WishlistService {
    Response<List<RespWishlist>> getWishlistList();

    Response addWishlist(Long productId, Long customerId);

    Response deleteWishlist(Long productId, Long customerId);

    Response<List<RespWishlist>> listByCustomerId(Long customerId);
}
