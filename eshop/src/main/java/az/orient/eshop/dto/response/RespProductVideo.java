package az.orient.eshop.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespProductVideo {
    private Long id;
    private String fileName;
    private String fileType;
    private RespProductDetails respProductDetails;
}
