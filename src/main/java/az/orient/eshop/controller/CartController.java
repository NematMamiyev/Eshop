package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqCart;
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

    @GetMapping("/list/{customerId}")
    public Response<RespCart> listByCustomerId(@PathVariable Long customerId){
        return cartService.listByCustomerId(customerId);
    }

    @PostMapping("/add")
    public Response addCart(@RequestBody ReqCart reqCart){
        return cartService.addCart(reqCart);
    }

    @PutMapping("delete")
    public Response deleteCart(@RequestBody ReqCart reqCart){
        return cartService.deleteCart(reqCart);
    }

}
