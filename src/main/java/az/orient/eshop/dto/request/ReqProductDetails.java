package az.orient.eshop.dto.request;


import az.orient.eshop.enums.Currency;
import az.orient.eshop.validation.ColorActive;
import az.orient.eshop.validation.ProductActive;
import az.orient.eshop.validation.SizeActive;
import az.orient.eshop.validation.ValidCurrency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ReqProductDetails {
    @NotNull(message = "Product id is required")
    @ProductActive
    private Long productId;
    @NotNull(message = "Size id is required")
    @SizeActive
    private Long sizeId;
    @NotNull(message = "Color id is required")
    @ColorActive
    private Long colorId;
    @NotNull(message = "Price is required")
    @Positive(message ="Price must be positive")
    private BigDecimal price;
    @NotNull(message = "Currency is required")
    @ValidCurrency
    private Currency currency;
    @NotNull(message = "stock is required")
    @Positive(message ="Stock must be positive")
    private Integer stock;
}
