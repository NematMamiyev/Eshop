package az.orient.eshop.dto.request;

import lombok.Data;

@Data
public class ReqEmployee {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String position;
    private String password;
}
