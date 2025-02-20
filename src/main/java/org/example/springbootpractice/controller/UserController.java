package org.example.springbootpractice.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbootpractice.dto.AccountDto;
import org.example.springbootpractice.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final AccountService accountService;

    @GetMapping("/user")
    public String user(Model model) {
        List<AccountDto> accounts = accountService.findAll();
        model.addAttribute("accounts", accounts);
        return "trainer/list-trainer";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteAccount(@PathVariable String id, Model model) {
        accountService.deleteAccount(id);
        model.addAttribute("accounts", accountService.findAll());
        return "trainer/list-trainer";
    }
}
