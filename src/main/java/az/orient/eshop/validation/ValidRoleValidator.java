package az.orient.eshop.validation;

import az.orient.eshop.enums.Role;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


    public class ValidRoleValidator implements ConstraintValidator<ValidRole, Role> {
        @Override
        public boolean isValid(Role role, ConstraintValidatorContext constraintValidatorContext) {
            return Role.isValidRole(role.getValue());
        }

        @Override
        public void initialize(ValidRole constraintAnnotation) {
            ConstraintValidator.super.initialize(constraintAnnotation);
        }
    }
