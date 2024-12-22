package az.orient.eshop.enums;

import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum Gender {

    MALE(0), FEMALE(1), CHILD(2);

    private Integer value;

    public static boolean isValidGender(Integer value) {
        for (Gender gender : Gender.values()) {
            if (gender.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
