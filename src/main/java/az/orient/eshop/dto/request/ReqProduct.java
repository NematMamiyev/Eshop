package az.orient.eshop.dto.request;
import az.orient.eshop.enums.Gender;
import az.orient.eshop.validation.BrandActive;
import az.orient.eshop.validation.SubcategoryActive;
import az.orient.eshop.validation.ValidGender;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.Date;

@Data
public class ReqProduct {
    @NotBlank(message = "Name is required")
    @Size(min = 2,max = 70)
    private String name;
    private String productInformation;
    @Future(message = "Expiration  date is required")
    private Date expirationDate;
    @NotNull(message = "Gender is required")
    @ValidGender
    private Gender gender;
    @BrandActive
    @NotNull(message = "Brand id is required")
    private Long brandId;
    @SubcategoryActive
    @NotNull(message = "Subcategory id is required")
    private Long subcategoryId;
}
