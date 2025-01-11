package az.orient.eshop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReqSize {
    @NotBlank(message = "Name is required")
    private String name;
}
