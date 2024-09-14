package az.orient.eshop.enums;

import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;

public enum Currency {
    AZN(0), USD(1), EURO(2);

    private final Integer value;

    Currency(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
    public static Currency fromValue(Integer value) {
        for (Currency currency : values()) {
            if (currency.getValue() == value) {
                return currency;
            }
        }
        throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid value");
    }
}
