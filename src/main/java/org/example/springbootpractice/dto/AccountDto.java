package org.example.springbootpractice.dto;

import lombok.Data;

@Data
public class AccountDto {
    private String id;
    private String username;
    private String email;
    private String password;
    private String role;
}
