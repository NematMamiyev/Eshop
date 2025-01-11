package az.orient.eshop.controller;
import az.orient.eshop.dto.response.RespCart;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.CartService;
import az.orient.eshop.validation.ProductDetailsActive;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping()
    public Response<RespCart> listByCustomerId(HttpServletRequest httpServletRequest){
        return cartService.listByCustomerId(httpServletRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping("/add/{productDetailsId}")
    public RespStatus addCart(@PathVariable @NotNull(message = "Id is required") @ProductDetailsActive Long productDetailsId, HttpServletRequest httpServletRequest ){
        return cartService.addCart(productDetailsId,httpServletRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @DeleteMapping("/delete/{productDetailsId}")
    public RespStatus deleteCart(@PathVariable @NotNull(message = "Id is required") @ProductDetailsActive Long productDetailsId, HttpServletRequest httpServletRequest){
        return cartService.deleteCart(productDetailsId,httpServletRequest);
    }

}
