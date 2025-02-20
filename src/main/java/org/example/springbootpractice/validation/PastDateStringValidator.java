package org.example.springbootpractice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PastDateStringValidator implements ConstraintValidator<PastDate, String> {
    @Override
    public void initialize(PastDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // Cho phép trường rỗng là hợp lệ (tùy chọn), nếu muốn bắt buộc nhập, hãy return false ở đây hoặc dùng @NotBlank
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Định dạng ngày tháng năm bạn mong đợi
            LocalDate date = LocalDate.parse(value, formatter); // Parse String thành LocalDate
            LocalDate today = LocalDate.now(); // Lấy ngày hiện tại

            return date.isBefore(today) || date.isEqual(today); // Kiểm tra xem ngày đã parse có trước ngày hiện tại không
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
