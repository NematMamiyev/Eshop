package az.orient.eshop.dto.response;

import az.orient.eshop.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespCustomer {
    private Long id;
    private String name;
    private String surname;
    private Date dob;
    private String email;
    private String phone;
    private String address;
    private Float height;
    private Float weight;
    private Gender gender;
}
