package az.orient.eshop.dto.response;

import az.orient.eshop.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespOrder {
    private Long id;
    private List<RespProductDetails> respProductDetailsList;
    private BigDecimal amount;
}
