package org.example.springbootpractice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueClassNameValidator.class)
public @interface UniqueClassName {
    String message() default "Class name already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
