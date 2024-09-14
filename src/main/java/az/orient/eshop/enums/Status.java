package az.orient.eshop.enums;

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

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
