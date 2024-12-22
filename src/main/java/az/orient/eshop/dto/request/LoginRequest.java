package az.orient.eshop.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @Email(message = "Email is required")
    @NotBlank(message = "Email is required")
    private String mail;
    @NotBlank(message = "Password is required")
    private String password;
}
