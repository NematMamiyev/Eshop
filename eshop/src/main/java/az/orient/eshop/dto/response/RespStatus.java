package az.orient.eshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespStatus {

    private Integer code;
    private String message;

    public static RespStatus getSuccessMessage() {
        return new RespStatus(1,"success");
    }
}