package az.orient.eshop.controller;

import az.orient.eshop.dto.response.RespProductDetails;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.WishlistService;
import az.orient.eshop.validation.ProductDetailsActive;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlists")
public class WishlistController {

    private final WishlistService wishlistService;

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping
    public Response<List<RespProductDetails>> listByCustomerId(HttpServletRequest httpServletRequest){
        return wishlistService.listByCustomerId(httpServletRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping
    public RespStatus addWishlist(@NotNull(message = "Product details id is required")
                                      @ProductDetailsActive
                                      Long productDetailsId, HttpServletRequest httpServletRequest){
        return wishlistService.addWishlist(productDetailsId,httpServletRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @DeleteMapping
    public RespStatus deleteWishlist(@NotNull(message = "Product details id is required")
                                         @ProductDetailsActive
                                         Long productDetailsId, HttpServletRequest httpServletRequest){
        return wishlistService.deleteWishlist(productDetailsId,httpServletRequest);
    }
}
