package az.orient.eshop.dto.response;

import az.orient.eshop.entity.*;
import az.orient.eshop.enums.Gender;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class RespProduct {
    private Long id;
    private String name;
    private String productInformation;
    private Date expertionDate;
    private Gender gender;
    private RespBrand respBrand;
    private List<RespProductDetails> respProductDetailsList;
    private RespSubcategory respSubcategory;
}
