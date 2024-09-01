package az.orient.eshop.controller;

import az.orient.eshop.dto.response.RespWishlist;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;

    @GetMapping("/list")
    public Response<List<RespWishlist>> getWishlistList(){
        return wishlistService.getWishlistList();
    }

    @GetMapping("/list/customer{customerId}")
    public Response<List<RespWishlist>> listByCustomerId(@PathVariable Long customerId){
        return wishlistService.listByCustomerId(customerId);
    }

    @PostMapping("/add/product{productId}/customer{customerId}")
    public Response addWishlist(@PathVariable Long productId,@PathVariable Long customerId){
        return wishlistService.addWishlist(productId,customerId);
    }

    @PutMapping("/delete/product{productId}/customer{customerId}")
    public Response deleteWishlist(@PathVariable Long productId,@PathVariable Long customerId){
        return wishlistService.deleteWishlist(productId,customerId);
    }
}
