package az.orient.eshop.enums;

import az.orient.eshop.exception.EshopException;
import az.orient.eshop.exception.ExceptionConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum PaymentMethod {
    ONLINE("Online ödəniş edilib",0),
    CASH_ON_DELIVERY("Qapıda nağd ödəniş ediləcək",1),
    POST_TERMINAL("Qapıda post terminalla ödəniş edilecek",2);

    private String description;
    private Integer value;

    public static boolean isValidPaymentMethod(Integer value){
        for (PaymentMethod paymentMethod : PaymentMethod.values()){
            if (paymentMethod.getValue().equals(value)){
                return true;
            }
        }
        return false;
    }
}
