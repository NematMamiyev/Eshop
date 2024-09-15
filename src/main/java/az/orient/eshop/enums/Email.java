package az.orient.eshop.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum Email {

    ORDERED("Məhsul sifariş verildi"),
    CONFIRMED("Sifarişiniz təsdiqləndi"),
    SHIPPED("Sifarişiniz karqoya verildi"),
    IN_CARGO_CENTER(" Sifarişiniz kargo mərkəzində"),
    COURIER_ON_WAY("Kuryer yolda"),
    DELIVERED("Sifarişiniz təslim edildi"),
    CANCELLED("Sifarişiniz ləğv edildi"),
    PAYMENT_FAILED("Ödəniş uğursuz");

    private final String description;

    Email(String description) {
        this.description = description;
    }

}
