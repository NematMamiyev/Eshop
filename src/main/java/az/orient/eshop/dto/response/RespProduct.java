package az.orient.eshop.dto.response;

import az.orient.eshop.enums.Gender;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RespProduct {
    private Long id;
    private String name;
    private Float price;
    private String productInformation;
    private Date expertionDate;
    private Gender gender;
    private String productNumber;
    private byte[] image;
    private byte[] video;
    private RespBrand respBrand;
    private RespSize respSize;
    private RespColor respColor;
    private RespSubcategory respSubcategory;
    private Integer count;
}
