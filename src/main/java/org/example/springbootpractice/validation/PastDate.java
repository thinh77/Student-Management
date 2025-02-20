package org.example.springbootpractice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PastDateStringValidator.class) // Liên kết với class validator
@Target({ElementType.FIELD}) // Áp dụng cho method và field
@Retention(RetentionPolicy.RUNTIME) // Giữ lại annotation lúc runtime
public @interface PastDate {
    String message() default "Date of birth must be in the past"; // Message lỗi mặc định

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
