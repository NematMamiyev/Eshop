package az.orient.eshop.controller;

import az.orient.eshop.dto.request.ReqWishlist;
import az.orient.eshop.dto.response.RespProductDetails;
import az.orient.eshop.dto.response.RespStatus;
import az.orient.eshop.dto.response.Response;
import az.orient.eshop.service.WishlistService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlists")
public class WishlistController {

    private final WishlistService wishlistService;

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/{customerId}")
    public Response<List<RespProductDetails>> listByCustomerId(@PathVariable @NotNull(message = "Id is required") Long customerId){
        return wishlistService.listByCustomerId(customerId);
    }
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping
    public RespStatus addWishlist(@RequestBody @Valid ReqWishlist reqWishlist){
        return wishlistService.addWishlist(reqWishlist);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @DeleteMapping
    public RespStatus deleteWishlist(@RequestBody @Valid ReqWishlist reqWishlist){
        return wishlistService.deleteWishlist(reqWishlist);
    }
}
