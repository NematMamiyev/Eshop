package az.orient.eshop.dto.request;

import az.orient.eshop.validation.CustomerActive;
import az.orient.eshop.validation.ProductDetailsActive;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReqWishlist {
    @NotNull(message = "Product details id is required")
    @ProductDetailsActive
    private Long productDetailsId;
    @NotNull(message = "Customer id is required")
    @CustomerActive
    private Long customerId;
}
