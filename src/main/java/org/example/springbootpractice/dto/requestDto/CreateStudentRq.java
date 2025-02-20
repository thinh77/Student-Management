package org.example.springbootpractice.dto.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.example.springbootpractice.validation.PastDate;
import org.example.springbootpractice.validation.UniqueIdCardNumber;
import org.example.springbootpractice.validation.UniquePhone;

@Data
public class CreateStudentRq {
    @Pattern(regexp = "^[a-zA-ZÀ-ỹ ]{1,50}$", message = "First name must be alphabets and less than 50 characters")
    private String firstName;
    @Pattern(regexp = "^[a-zA-ZÀ-ỹ ]{1,50}$", message = "Last name must be alphabets and less than 50 characters")
    private String lastName;
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female or Other")
    private String gender;
    @PastDate
    private String dob;
    @Pattern(regexp = "^[0-9]{9}$|^[0-9]{12}$", message = "Id card number must be 9 digits or 12 digits")
    @UniqueIdCardNumber
    private String idCardNumber;
    @Email(message = "Email must be in correct format")
//    @UniqueEmail
    private String email;
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    @UniquePhone
    private String phone;
    @Pattern(regexp = "^[a-zA-ZÀ-ỹ0-9 ,.]{1,100}$", message = "Address must be alphabets and less than 100 characters")
    private String address;
    private String className;
}
