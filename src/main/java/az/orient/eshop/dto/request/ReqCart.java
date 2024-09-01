package az.orient.eshop.dto.request;

import az.orient.eshop.dto.response.RespCustomer;
import az.orient.eshop.dto.response.RespProduct;
import lombok.Data;

@Data
public class ReqCart {
    private Long id;
    private Long productId;
    private Long customerId;
}
