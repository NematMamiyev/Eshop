package az.orient.eshop.dto.response;


import az.orient.eshop.entity.ProductDetails;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RespCart {
    private List<RespProductDetails> respProductDetailsList;
    private Float amount;
}
