package az.orient.eshop.dto.request;

import az.orient.eshop.enums.Gender;
import az.orient.eshop.validation.ValidGender;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

@Data
public class ReqCustomer {
    @NotBlank(message = "Name is required")
    @Size(min = 2,max = 50,message = "Name must be between 2 and 70 characters")
    private String name;
    @NotBlank(message = "Surname is required")
    @Size(min = 2,max = 50,message = "Name must be between 2 and 70 characters")
    private String surname;
    @Past(message = "Dob is required")
    private Date dob;
    @Email(message = "Email is required")
    @NotBlank(message = "Email is required")
    private String email;
    @Size(min = 2,max = 20,message = "Phone must be between 2 and 70 characters")
    @NotBlank(message = "Phone is required")
    private String phone;
    @Size(min = 15, max = 150, message = "Address is required")
    private String address;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,20}$",
            message = "Password must be 8-20 characters long, contain at least one digit, one lowercase letter, one uppercase letter, and one special character.")
    private String password;
    private Float height;
    private Float weight;
    @NotNull(message = "Gender is required")
    @ValidGender
    private Gender gender;
}
