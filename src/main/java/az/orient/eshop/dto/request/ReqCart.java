package az.orient.eshop.dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReqCart {
    @NotNull(message = "Id is required")
    private Long productDetailsId;
    @NotNull(message = "Id is required")
    private Long customerId;
}
