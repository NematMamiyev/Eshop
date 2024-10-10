package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqWishlist;
import az.orient.eshop.dto.response.RespWishlist;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlists")
public class WishlistController {
    private final WishlistService wishlistService;

    @GetMapping("/{customerId}")
    public Response<RespWishlist> listByCustomerId(@PathVariable Long customerId){
        return wishlistService.listByCustomerId(customerId);
    }

    @PostMapping
    public Response addWishlist(@RequestBody ReqWishlist reqWishlist){
        return wishlistService.addWishlist(reqWishlist);
    }

    @DeleteMapping
    public Response deleteWishlist(@RequestBody ReqWishlist reqWishlist){
        return wishlistService.deleteWishlist(reqWishlist);
    }
}
