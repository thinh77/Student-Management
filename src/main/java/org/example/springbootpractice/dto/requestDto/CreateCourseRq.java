package org.example.springbootpractice.dto.requestDto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateCourseRq {
    @Pattern(regexp = "^[a-zA-Z0-9]{5,50}$", message = "Course name must be alphanumeric and between 5-50 characters")
    private String name;
    @Pattern(regexp = "^[a-zA-Z0-9]{5,10}$", message = "Course code must be alphanumeric and between 5-10 characters")
    private String code;
    @Pattern(regexp = "^[a-zA-Z0-9 ]{10,200}$", message = "Description must be alphanumeric and between 10-200 characters")
    private String description;
    private String startDate;
    private String endDate;
    private String className;
    private String trainerName;
}
