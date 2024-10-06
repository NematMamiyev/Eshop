package az.orient.eshop.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Builder
public class RespWareHouseWork {
    private Long id;
    private RespOrder respOrder;
    private Date dataDate;
}
