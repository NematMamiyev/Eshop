package az.orient.eshop.dto.request;

import az.orient.eshop.validation.ProductDetailsActive;
import az.orient.eshop.validation.ShelfActive;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReqShelfProductDetails {
    @NotNull(message = "Shelf id is required")
    @ShelfActive
    private Long shelfId;
    @NotNull(message = "Shelf id is required")
    @ProductDetailsActive
    private Long productDetailsId;
}
