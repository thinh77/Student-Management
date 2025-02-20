package org.example.springbootpractice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springbootpractice.dto.requestDto.CreateAccountDto;
import org.example.springbootpractice.service.AccountService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AccountService accountService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("account", new CreateAccountDto());
        return "auth/register";
    }

    @PostMapping("/register")
    public String createUser(@ModelAttribute("account") @Valid CreateAccountDto account, BindingResult result) {
        if (result.hasErrors()) {
            return "auth/register";
        }
        accountService.register(account);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();

        if (Objects.equals(role, "Admin")) {
            return "redirect:/admin";
        } else if (Objects.equals(role, "Trainer")) {
            return "redirect:/trainer";
        }

        String loginError = (String) request.getSession().getAttribute("loginError");
        if (loginError != null) {
            model.addAttribute("loginError", loginError);
            request.getSession().removeAttribute("loginError");
        }
        return "auth/login";
    }

    @GetMapping("/")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();
        if (Objects.equals(role, "Admin")) {
            model.addAttribute("page", 0);
            return "redirect:/admin";
        } else if (Objects.equals(role, "Trainer")) {
            model.addAttribute("page", 0);
            return "redirect:/trainer";
        }
        return "home";
    }
}
