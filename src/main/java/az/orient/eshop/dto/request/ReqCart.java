package az.orient.eshop.dto.request;
import az.orient.eshop.validation.CustomerActive;
import az.orient.eshop.validation.ProductDetailsActive;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReqCart {
    @NotNull(message = "Id is required")
    @ProductDetailsActive
    private Long productDetailsId;
    @NotNull(message = "Id is required")
    @CustomerActive
    private Long customerId;
}
