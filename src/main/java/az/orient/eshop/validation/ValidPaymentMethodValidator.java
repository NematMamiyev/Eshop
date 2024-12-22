package az.orient.eshop.validation;

import az.orient.eshop.enums.PaymentMethod;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

public class ValidPaymentMethodValidator implements ConstraintValidator<ValidPaymentMethod, PaymentMethod> {
    @Override
    public void initialize(ValidPaymentMethod constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Override
    public boolean isValid(PaymentMethod paymentMethod, ConstraintValidatorContext constraintValidatorContext) {
        return PaymentMethod.isValidPaymentMethod(paymentMethod.getValue());
    }
}
