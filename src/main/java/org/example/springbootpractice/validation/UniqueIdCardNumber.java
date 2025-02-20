package org.example.springbootpractice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueIdCardNumberValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueIdCardNumber {
    String message() default "Id card number is already existed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
