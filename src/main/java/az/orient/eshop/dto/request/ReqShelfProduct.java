package az.orient.eshop.dto.request;

import lombok.Data;

@Data
public class ReqShelfProduct {
    private Long id;
    private Long shelfId;
    private Long productId;
    private Long warehouseId;
}
