package org.example.springbootpractice.dto.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class CreateAccountDto {
    @Pattern(regexp = "^[a-zA-Z0-9]{5,20}$", message = "Username must be alphanumeric and between 5-20 characters")
    private String username;
    @Email(message = "Email should be valid")
    private String email;
    @Pattern(regexp = "^[a-zA-Z0-9]{8,16}$", message = "Password must be alphanumeric and between 8-16 characters")
    private String password;
    private String role;
}
