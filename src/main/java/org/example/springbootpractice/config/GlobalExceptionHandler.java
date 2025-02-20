package org.example.springbootpractice.config;

import org.example.springbootpractice.exception.DuplicateEmailException;
import org.example.springbootpractice.exception.DuplicateIdCardNumberException;
import org.example.springbootpractice.exception.DuplicatePhoneException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateIdCardNumberException.class)
    public String handleDuplicateIdCardNumberException(DuplicateIdCardNumberException e) {
        return e.getMessage();
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public String handleDuplicateEmailException(DuplicateEmailException e) {
        return e.getMessage();
    }

    @ExceptionHandler(DuplicatePhoneException.class)
    public String handleDuplicatePhoneException(DuplicatePhoneException e) {
        return e.getMessage();
    }
}
