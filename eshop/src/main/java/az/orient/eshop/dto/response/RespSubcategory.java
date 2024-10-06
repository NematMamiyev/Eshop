package az.orient.eshop.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespSubcategory {
    private Long id;
    private String name;
    private RespCategory respCategory;
}
