package az.orient.eshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespShelf {
    private Long id;
    private String name;
    private RespWarehouse respWarehouse;
}
