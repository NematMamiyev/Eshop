package az.orient.eshop.dto.request;

import az.orient.eshop.validation.WarehouseActive;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReqShelf {
    @NotBlank(message = "Name is required")
    @Size(min = 2,max = 70,message = "Name must be between 2 and 70 characters")
    private String name;
    @NotNull(message = "Warehouse id is required")
    @WarehouseActive
    private Long warehouseId;
}
