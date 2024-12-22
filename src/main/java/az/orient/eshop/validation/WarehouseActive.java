package az.orient.eshop.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = WarehouseActiveValidator.class)
@Target({ElementType.PARAMETER, ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WarehouseActive {
    String message() default "Warehouse not found";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
