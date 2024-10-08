package az.orient.eshop.enums;

import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import lombok.Getter;

@Getter
public enum Gender {

    MALE(0), FEMALE(1), CHILD(2);

    private final Integer value;

    Gender(Integer value) {
        this.value = value;
    }

    public static Gender fromValue(Integer value) {
        for (Gender gender : values()) {
            if (gender.getValue().equals(value)) {
                return gender;
            }
        }
        throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid value");
    }
}
