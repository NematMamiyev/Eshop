package az.orient.eshop.validation;

import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.repository.ShelfRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShelfActiveValidator implements ConstraintValidator<ShelfActive, Long> {

    private final ShelfRepository shelfRepository;

    @Override
    public void initialize(ShelfActive constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        return shelfRepository.existsShelfByIdAndActive(aLong, EnumAvailableStatus.ACTIVE.getValue());
    }
}
