package az.orient.eshop.dto.request;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReqProductAndProductDetailsList {
    private ReqProduct reqProduct;
    private List<ReqProductDetails> reqProductDetailsList = new ArrayList<>();
}
