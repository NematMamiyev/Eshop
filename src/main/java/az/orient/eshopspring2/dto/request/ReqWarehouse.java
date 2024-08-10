package az.orient.eshopspring2.dto.request;

import az.orient.eshopspring2.dto.response.RespShelf;
import lombok.Data;

@Data
public class ReqWarehouse {
    private Long id;
    private String name;
    private String address;
}
