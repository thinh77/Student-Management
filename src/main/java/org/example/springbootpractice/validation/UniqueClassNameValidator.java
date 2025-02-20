package org.example.springbootpractice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.springbootpractice.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueClassNameValidator implements ConstraintValidator<UniqueClassName, String> {
    @Autowired
    private ClassRepository classRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return !classRepository.existsByClassName(value);
    }
}
