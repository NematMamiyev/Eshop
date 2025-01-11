package az.orient.eshop.dto.request;

import az.orient.eshop.enums.Role;
import az.orient.eshop.validation.ValidRole;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ReqEmployee {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "The name length is incorrect")
    private String name;
    @NotBlank(message = "Surname is required")
    @Size(min = 2, max = 100, message = "The surname length is incorrect")
    private String surname;
    @Email(message = "Email is required")
    @NotBlank
    private String email;
    @Size(min = 10, max = 20, message = "Phone number is required")
    @NotBlank(message = "Phone is required")
    private String phone;
    @ValidRole
    @NotNull(message = "Role is required")
    private Role role;
    //@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,20}$",
  //          message = "Password must be 8-20 characters long, contain at least one digit, one lowercase letter, one uppercase letter, and one special character.")
    private String password;
}
