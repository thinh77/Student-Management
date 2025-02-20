package org.example.springbootpractice.service;

import org.example.springbootpractice.dto.AccountDto;
import org.example.springbootpractice.dto.requestDto.CreateAccountDto;

import java.util.List;

public interface AccountService {
    void register(CreateAccountDto createAccountDto);
    List<AccountDto> findAll();
    List<AccountDto> findAllByRole(String role);
    void deleteAccount(String id);
}
