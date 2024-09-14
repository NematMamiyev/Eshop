package az.orient.eshop.dto.response;

import az.orient.eshop.enums.Status;
import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Builder
public class RespOrderStatus {
    private Long id;
    private Status status;
    private RespOrder respOrder;
    private Date dataDate;
}
