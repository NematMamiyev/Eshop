package az.orient.eshopspring2.dto.response;

import az.orient.eshopspring2.entity.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespSubcategory {
    private Long id;
    private String name;
    private RespCategory respCategory;
}
