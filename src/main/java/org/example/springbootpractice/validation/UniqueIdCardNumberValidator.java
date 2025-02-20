package org.example.springbootpractice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.springbootpractice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueIdCardNumberValidator implements ConstraintValidator<UniqueIdCardNumber, String> {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return !studentRepository.existsByIdCardNumber(value);
    }
}
