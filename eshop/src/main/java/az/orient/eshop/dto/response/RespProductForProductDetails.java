package az.orient.eshop.dto.response;

import az.orient.eshop.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespProductForProductDetails {
    private Long id;
    private String name;
    private String productInformation;
    private Date expertionDate;
    private Gender gender;
    private RespBrand respBrand;
    private RespSubcategory respSubcategory;
}
