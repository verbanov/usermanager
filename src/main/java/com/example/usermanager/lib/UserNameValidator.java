package com.example.usermanager.lib;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNameValidator implements ConstraintValidator<ValidUserName, String> {
    private static final String USERNAME_VALIDATION_REGEX = "^[a-zA-Z]{3,16}$";

    @Override
    public boolean isValid(String field, ConstraintValidatorContext context) {
        return field != null && field.matches(USERNAME_VALIDATION_REGEX);
    }
}
