package org.example.springbootpractice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.example.springbootpractice.validation.PastDate;

@Data
public class StudentDto {
    private String id;
    @Pattern(regexp = "^[a-zA-ZÀ-ỹ ]{1,50}$", message = "First name must be alphabets and less than 50 characters")
    private String firstName;
    @Pattern(regexp = "^[a-zA-ZÀ-ỹ ]{1,50}$", message = "Last name must be alphabets and less than 50 characters")
    private String lastName;
    private String gender;
    @PastDate
    private String dob;
    @Pattern(regexp = "^[0-9]{9}$|^[0-9]{12}$", message = "Id card number must be 9 digits or 12 digits")
    private String idCardNumber;
    @Email(message = "Email must be in correct format")
    private String email;
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phone;
    @Pattern(regexp = "^[a-zA-ZÀ-ỹ0-9 ,.]{1,100}$", message = "Address must be alphabets and less than 100 characters")
    private String address;
    private String avatarUrl;
    private String degreeUrl;
    private String className;
}
