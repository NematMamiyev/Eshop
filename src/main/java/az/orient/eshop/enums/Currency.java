package az.orient.eshop.enums;

import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import lombok.Getter;

@Getter
public enum Currency {
    AZN(0), USD(1), EURO(2);

    private final Integer value;

    Currency(Integer value) {
        this.value = value;
    }

    public static Currency fromValue(Integer value) {
        for (Currency currency : values()) {
            if (currency.getValue().equals(value)) {
                return currency;
            }
        }
        throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid value");
    }
}
