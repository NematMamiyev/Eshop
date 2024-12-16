package az.orient.eshop.dto.request;
import az.orient.eshop.enums.Gender;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ReqProduct {
    private String name;
    private String productInformation;
    private Date expertionDate;
    private Gender gender;
    private Long brandId;
    private Long subcategoryId;
}
