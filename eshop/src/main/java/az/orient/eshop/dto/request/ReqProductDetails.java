package az.orient.eshop.dto.request;


import az.orient.eshop.enums.Currency;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

@Data
public class ReqProductDetails {
    private Long id;
    private Long productId;
    private Long sizeId;
    private Long colorId;
    private Float price;
    private Currency currency;
    private Integer stock;
}
