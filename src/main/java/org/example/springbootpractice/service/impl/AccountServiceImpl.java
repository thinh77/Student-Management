package org.example.springbootpractice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.springbootpractice.dto.AccountDto;
import org.example.springbootpractice.dto.requestDto.CreateAccountDto;
import org.example.springbootpractice.entity.Account;
import org.example.springbootpractice.repository.AccountRepository;
import org.example.springbootpractice.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(CreateAccountDto createAccountDto) {
        if (this.accountRepository.existsByUsername(createAccountDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        Account account = this.modelMapper.map(createAccountDto, Account.class);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        this.accountRepository.save(account);
    }

    @Override
    public List<AccountDto> findAll() {
        List<Account> accounts = accountRepository.findAll();
        return accounts
                .stream()
                .map(account -> modelMapper.map(account, AccountDto.class))
                .toList();
    }

    @Override
    public List<AccountDto> findAllByRole(String role) {
        List<Account> accounts = accountRepository.findAllByRole(role);
        return accounts
                .stream()
                .map(account -> modelMapper.map(account, AccountDto.class))
                .toList();
    }

    @Override
    public void deleteAccount(String id) {
        accountRepository.deleteById(id);
    }
}
