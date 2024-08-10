package az.orient.eshopspring2.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespPaymentMethod {
    private Long id;
    private String name;
}
