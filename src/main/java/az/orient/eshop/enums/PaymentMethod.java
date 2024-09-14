package az.orient.eshop.enums;

import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;

public enum PaymentMethod {
    ONLINE("Online ödəniş edilib",0),
    CASH_ON_DELIVERY("Qapıda nağd ödəniş ediləcək",1),
    POST_TERMINAL("Qapıda post terminalla ödəniş edilecek",2);

    private final String description;
    private final Integer value;

    PaymentMethod(String description, Integer value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }
    public Integer getValue(){
        return value;
    }
    public static PaymentMethod fromValue(Integer value) {
        for (PaymentMethod paymentMethod : values()) {
            if (paymentMethod.getValue() == value) {
                return paymentMethod;
            }
        }
        throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid value");
    }
}
