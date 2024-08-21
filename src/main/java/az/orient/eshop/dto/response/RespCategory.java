package az.orient.eshop.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RespCategory {
    private Long id;
    private String name;
}
