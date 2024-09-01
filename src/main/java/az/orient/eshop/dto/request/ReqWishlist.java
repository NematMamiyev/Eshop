package az.orient.eshop.dto.request;

import lombok.Data;

@Data
public class ReqWishlist {
    private Long id;
    private Long productId;
    private Long customerId;
}
