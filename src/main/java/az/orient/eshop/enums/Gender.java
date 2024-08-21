package az.orient.eshop.enums;

public enum Gender {

    MALE(0), FEMALE(1), CHILD(2);

    private final int value;

    Gender(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Gender fromValue(int value) {
        for (Gender gender : values()) {
            if (gender.getValue() == value) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }
}
