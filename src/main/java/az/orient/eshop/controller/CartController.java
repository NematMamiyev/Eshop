package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqCart;
import az.orient.eshop.dto.response.RespCart;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.CartService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;

    @GetMapping("/{customerId}")
    public Response<RespCart> listByCustomerId(@PathVariable @NotNull(message = "Id is required") Long customerId){
        return cartService.listByCustomerId(customerId);
    }

    @PostMapping("/add")
    public Response addCart(@RequestBody @Valid ReqCart reqCart){
        return cartService.addCart(reqCart);
    }

    @DeleteMapping("delete")
    public Response deleteCart(@RequestBody @Valid ReqCart reqCart){
        return cartService.deleteCart(reqCart);
    }

}
