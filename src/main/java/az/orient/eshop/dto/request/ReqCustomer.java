package az.orient.eshop.dto.request;

import az.orient.eshop.enums.Gender;
import lombok.Data;
import java.util.Date;

@Data
public class ReqCustomer {
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
