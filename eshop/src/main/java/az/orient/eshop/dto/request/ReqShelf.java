package az.orient.eshop.dto.request;

import lombok.Data;

@Data
public class ReqShelf {
    private Long id;
    private String name;
    private Long warehouseId;
}
