package az.orient.eshop.dto.request;


import az.orient.eshop.enums.Currency;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ReqProductDetails {
    private Long productId;
    private Long sizeId;
    private Long colorId;
    private BigDecimal price;
    private Currency currency;
    private Integer stock;
}
