package az.orient.eshopspring2.dto.request;

import lombok.Data;

@Data
public class ReqSubcategory {
    private Long id;
    private String name;
    private Long categoryId;
}
