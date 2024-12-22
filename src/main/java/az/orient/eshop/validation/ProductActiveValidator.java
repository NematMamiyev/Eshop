package az.orient.eshop.validation;

import az.orient.eshop.enums.EnumAvailableStatus;
import az.orient.eshop.repository.ProductRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductActiveValidator implements ConstraintValidator<ProductActive,Long> {

    private final ProductRepository productRepository;

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        return productRepository.existsProductByIdAndActive(aLong, EnumAvailableStatus.ACTIVE.getValue());
    }

    @Override
    public void initialize(ProductActive constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
