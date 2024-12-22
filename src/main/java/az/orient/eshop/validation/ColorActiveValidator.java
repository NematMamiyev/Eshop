package az.orient.eshop.validation;

import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.repository.ColorRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ColorActiveValidator implements ConstraintValidator<ColorActive,Long> {

    private final ColorRepository colorRepository;

    @Override
    public void initialize(ColorActive constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        return colorRepository.existsColorByIdAndActive(aLong, EnumAvailableStatus.ACTIVE.getValue());
    }
}
