package az.orient.eshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespShelfProductDetails {
    private Long id;
    private RespShelf respShelf;
    private RespProductDetails respProductDetails;
}
