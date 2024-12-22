package az.orient.eshop.validation;

import az.orient.eshop.enums.Gender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

public class ValidGenderValidator implements ConstraintValidator<ValidGender, Gender> {
    @Override
    public boolean isValid(Gender gender, ConstraintValidatorContext constraintValidatorContext) {
        return Gender.isValidGender(gender.getValue());
    }

    @Override
    public void initialize(ValidGender constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
