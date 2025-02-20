package org.example.springbootpractice.dto.requestDto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.example.springbootpractice.validation.UniqueClassName;

@Data
public class CreateClassRq {
    @Pattern(regexp = "^[a-zA-Z0-9]{5,20}$", message = "Class name must be alphanumeric and between 5-20 characters")
    @UniqueClassName
    private String className;
    @Pattern(regexp = "^[a-zA-Z0-9 ]{10,200}$", message = "Description must be alphanumeric and between 10-200 characters")
    private String description;
}
