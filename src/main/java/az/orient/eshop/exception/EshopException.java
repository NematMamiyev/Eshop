package az.orient.eshop.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EshopException extends RuntimeException{
        private Integer code;
        private String message;
}
