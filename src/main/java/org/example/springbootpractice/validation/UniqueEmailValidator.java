package org.example.springbootpractice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.springbootpractice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !studentRepository.existsByEmail(value);
    }
}
