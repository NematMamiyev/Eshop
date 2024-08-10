package az.orient.eshopspring2.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum EnumAvailableStatus {
    ACTIVE(1) , DEACTIVE(0);

    private int value;

    public int getValue() {
        return value;
    }
}
