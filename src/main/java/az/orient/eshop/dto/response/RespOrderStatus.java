package az.orient.eshop.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespOrderStatus {
    private Long id;
    private String name;
}
