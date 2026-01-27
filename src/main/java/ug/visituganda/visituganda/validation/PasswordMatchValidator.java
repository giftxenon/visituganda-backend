/*
package ug.visituganda.visituganda.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ug.visituganda.visituganda.dto.request.RegisterRequest;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, RegisterRequest> {
    private String password;
    private String confirmPassword;

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        this.password = constraintAnnotation.password();
        this.confirmPassword = constraintAnnotation.confirmPassword();
    }

    @Override
    public boolean isValid(RegisterRequest dto, ConstraintValidatorContext context) {
        if (dto.getPassword() == null || dto.getConfirmPassword() == null) {
            return false;
        }
        return dto.getPassword().equals(dto.getConfirmPassword());
    }
}*/
