package az.orient.eshop.controller;

import az.orient.eshop.dto.response.RespCart;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @GetMapping("/cart/list")
    public Response<List<RespCart>> getCartList(){
        return cartService.getCartList();
    }

    @GetMapping("/cart/list/customer{customerId}")
    public Response<List<RespCart>> listByCustomerId(@PathVariable Long customerId){
        return cartService.listByCustomerId(customerId);
    }

    @PostMapping("/cart/add/product{productId}/customer{customerId}")
    public Response addCart(@PathVariable Long productId, @PathVariable Long customerId){
        return cartService.addCart(productId, customerId);
    }

    @PutMapping("/cart/delete/product{productId}/customer{customerId}")
    public Response deleteCart(@PathVariable Long productId, @PathVariable Long customerId){
        return cartService.deleteCart(productId, customerId);
    }

}
