package az.orient.eshop.dto.request;


import az.orient.eshop.enums.Gender;
import lombok.Data;

import java.util.Date;

@Data
public class ReqProduct {
    private Long id;
    private String name;
    private Float price;
    private String productInformation;
    private Date expertionDate;
    private Gender gender;
    private String productNumber;
    private byte[] image;
    private byte[] video;
    private Long brandId;
    private Long sizeId;
    private Long colorId;
    private Long subcategoryId;
    private Integer count;
}
