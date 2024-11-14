package az.orient.eshop.enums;

import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum Role {
    SUPER_ADMIN(0), ADMIN(1), OPERATOR(2), CUSTOMER(3);

    private Integer value;

    public static Role fromValue(Integer value) {
        for (Role role : values()) {
            if (role.getValue().equals(value)) {
                return role;
            }
        }
        throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid value");
    }
}
