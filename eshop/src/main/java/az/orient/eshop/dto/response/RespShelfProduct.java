package az.orient.eshop.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespShelfProduct {
    private Long id;
    private RespShelf respShelf;
    private RespProductDetails respProductDetails;
    private RespWarehouse respWarehouse;
}
