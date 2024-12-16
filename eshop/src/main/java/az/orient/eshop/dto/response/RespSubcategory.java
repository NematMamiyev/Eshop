package az.orient.eshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespSubcategory {
    private Long id;
    private String name;
    private RespCategory respCategory;
}
