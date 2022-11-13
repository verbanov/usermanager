package com.example.usermanager.lib;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    private static final String PASSWORD_VALIDATION_REGEX
            = "(?=^.{3,16}$)(?=.*\\d)(?![.\\n])(?=.*[a-z]).*";

    @Override
    public boolean isValid(String field, ConstraintValidatorContext context) {
        return field != null && field.matches(PASSWORD_VALIDATION_REGEX);
    }
}
