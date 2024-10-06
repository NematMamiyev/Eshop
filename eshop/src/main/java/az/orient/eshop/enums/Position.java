package az.orient.eshop.enums;

import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum Position {
    SUPER_ADMIN(0), ADMIN(1), OPERATOR(2);

    private Integer value;

    public static Position fromValue(Integer value) {
        for (Position position : values()) {
            if (position.getValue().equals(value)) {
                return position;
            }
        }
        throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid value");
    }
}
