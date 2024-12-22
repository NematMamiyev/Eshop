package az.orient.eshop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum Email {

    ORDERED("Məhsul sifariş verildi"),
    CONFIRMED("Sifarişiniz təsdiqləndi"),
    SHIPPED("Sifarişiniz karqoya verildi"),
    IN_CARGO_CENTER(" Sifarişiniz kargo mərkəzində"),
    COURIER_ON_WAY("Kuryer yolda"),
    DELIVERED("Sifarişiniz təslim edildi"),
    CANCELLED("Sifarişiniz ləğv edildi"),
    PAYMENT_FAILED("Ödəniş uğursuz");

    private  String description;
}
