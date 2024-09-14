package az.orient.eshop.dto.request;
import lombok.Data;

@Data
public class ReqCart {
    private Long id;
    private Long productDetailsId;
    private Long customerId;
}
