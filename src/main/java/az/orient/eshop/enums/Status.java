package az.orient.eshop.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    ORDERED("Sifariş verildi"),
    CONFIRMED("Sifariş təsdiqləndi"),
    SHIPPED("Karqoya verildi"),
    IN_CARGO_CENTER("Kargo mərkəzində"),
    COURIER_ON_WAY("Kuryer yolda"),
    DELIVERED("Təslim edildi"),
    CANCELLED("Ləğv edildi"),
    PAYMENT_FAILED("Ödəniş uğursuz");

    private final String description;

}
