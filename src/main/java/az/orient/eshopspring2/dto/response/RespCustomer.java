package az.orient.eshopspring2.dto.response;

import az.orient.eshopspring2.enums.Gender;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RespCustomer {
    private Long id;
    private String name;
    private String surname;
    private Date dob;
    private String email;
    private String phone;
    private String address;
    private String password;
    private Float height;
    private Float weight;
    private Gender gender;
}
