package az.orient.eshop.validation;

import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.repository.SubcategoryRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SubcategoryActiveValidator implements ConstraintValidator<SubcategoryActive,Long> {

    private final SubcategoryRepository subcategoryRepository;
    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        return subcategoryRepository.existsSubcategoryByIdAndActive(aLong, EnumAvailableStatus.ACTIVE.getValue());
    }

    @Override
    public void initialize(SubcategoryActive constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
