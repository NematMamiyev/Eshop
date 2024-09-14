package az.orient.eshop.dto.response;

import az.orient.eshop.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RespOrder {
    private Long id;
    private List<RespProductDetails> respProductDetailsList;
    private RespCustomer respCustomer;
    private Float amount;
}
