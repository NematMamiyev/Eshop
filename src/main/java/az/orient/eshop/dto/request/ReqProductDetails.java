package az.orient.eshop.dto.request;


import az.orient.eshop.enums.Currency;
import lombok.Data;

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
    private Set<ReqProductImage> reqProductImageList = new HashSet<>();
    private Set<ReqProductVideo> reqProductVideoList = new HashSet<>();
}
