package az.orient.eshop.enums;

import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;

public enum Position {
    SUPER_ADMIN(0), ADMIN(1), OPERATOR(2);

    private final Integer value;

    Position(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
    public static Position fromValue(Integer value) {
        for (Position position : values()) {
            if (position.getValue() == value) {
                return position;
            }
        }
        throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid value");
    }
}
