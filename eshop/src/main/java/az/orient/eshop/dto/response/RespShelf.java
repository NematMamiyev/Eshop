package az.orient.eshop.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespShelf {
    private Long id;
    private String name;
    private RespWarehouse respWarehouse;
}
