package az.orient.eshop.exception;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EshopException extends RuntimeException{
        private Integer code;
        private String message;
}
