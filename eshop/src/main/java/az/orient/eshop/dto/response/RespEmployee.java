package az.orient.eshop.dto.response;

import az.orient.eshop.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespEmployee {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Role role;
    private String password;
}
