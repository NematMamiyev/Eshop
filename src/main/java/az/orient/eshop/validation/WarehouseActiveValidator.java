package az.orient.eshop.validation;

import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.repository.WarehouseRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WarehouseActiveValidator  implements ConstraintValidator<WarehouseActive,Long> {
    private final WarehouseRepository warehouseRepository;

    @Override
    public void initialize(WarehouseActive constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        return warehouseRepository.existsWarehouseByIdAndActive(aLong, EnumAvailableStatus.ACTIVE.getValue());
    }
}
