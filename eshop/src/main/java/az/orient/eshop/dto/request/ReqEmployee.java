package az.orient.eshop.dto.request;

import az.orient.eshop.enums.Position;
import lombok.Data;

@Data
public class ReqEmployee {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Position position;
    private String password;
}
