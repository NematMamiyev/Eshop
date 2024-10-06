package az.orient.eshop.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespWarehouse {
    private Long id;
    private String name;
    private String address;
}
