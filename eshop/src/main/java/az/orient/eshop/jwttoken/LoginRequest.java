package az.orient.eshop.jwttoken;

import lombok.Data;

@Data
public class LoginRequest {
    private String mail;
    private String password;
}
