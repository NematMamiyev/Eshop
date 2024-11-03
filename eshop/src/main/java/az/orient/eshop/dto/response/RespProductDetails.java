package az.orient.eshop.dto.response;

import az.orient.eshop.enums.Currency;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
public class RespProductDetails {
    private Long id;
    private RespProduct respProduct;
    private RespSize respSize;
    private RespColor respColor;
    private BigDecimal price;
    private Currency currency;
    private Integer stock;
}
