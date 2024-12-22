package az.orient.eshop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    SUPER_ADMIN(0), ADMIN(1), OPERATOR(2), CUSTOMER(3);

    private final Integer value;

    public static boolean isValidRole(Integer value) {
        for (Role role : Role.values()) {
            if (role.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
