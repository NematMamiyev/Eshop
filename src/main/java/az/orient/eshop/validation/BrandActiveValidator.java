package az.orient.eshop.validation;

import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.repository.BrandRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BrandActiveValidator implements ConstraintValidator<BrandActive,Long> {

    private final BrandRepository brandRepository;

    @Override
    public void initialize(BrandActive constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        return brandRepository.existsBrandByIdAndActive(aLong, EnumAvailableStatus.ACTIVE.getValue());
    }
}
