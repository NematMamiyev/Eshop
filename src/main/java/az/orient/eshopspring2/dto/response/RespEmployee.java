package az.orient.eshopspring2.dto.response;

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
    private String position;
    private String password;
}
