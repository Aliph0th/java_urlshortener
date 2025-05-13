package com.bsujava.servlet.validator;

import com.bsujava.servlet.entity.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public class UserValidator {
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public void validate(User user) {
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        if (!constraintViolations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed for user: ");
            for (ConstraintViolation<User> violation : constraintViolations) {
                errorMessage.append(violation.getMessage()).append(";");
            }
            throw new IllegalArgumentException(errorMessage.toString());
        }
    }
}
