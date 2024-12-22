package az.orient.eshop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReqBrand {
    @NotBlank(message = "Name is required")
    @Size(min = 2,max = 70,message = "Name must be between 2 and 70 characters")
    String name;
}
