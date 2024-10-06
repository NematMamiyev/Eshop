package az.orient.eshop.dto.response;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RespBrand {
    private Long id;
    private String name;
}
