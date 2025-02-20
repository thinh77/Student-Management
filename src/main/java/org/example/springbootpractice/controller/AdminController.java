package org.example.springbootpractice.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbootpractice.dto.StudentDto;
import org.example.springbootpractice.service.CourseService;
import org.example.springbootpractice.service.StudentService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final CourseService courseService;
    private final StudentService studentService;

    @GetMapping("/admin")
    public String admin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", authentication.getName());
        model.addAttribute("role", authentication.getAuthorities().stream().findFirst().get().getAuthority());
        List<StudentDto> studentDto = studentService.getStudentByPage(0);
        int numberOfPages = (int) Math.ceil(studentService.getAllStudents().size() / 14.0);
        model.addAttribute("numberOfPages", numberOfPages);
        model.addAttribute("students", studentDto);
        return "admin/admin";
    }

    @GetMapping("/trainer")
    public String getAllCoursesByTrainer(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return "redirect:/login";
        }
        String username = authentication.getName();
        model.addAttribute("username", username);
        model.addAttribute("role", authentication.getAuthorities().stream().findFirst().get().getAuthority());
        model.addAttribute("courses", courseService.findAllCoursesByTrainerName(username));
        model.addAttribute("page", 0);
        model.addAttribute("lastPage", ((int) Math.ceil(courseService.getNumberOfCourses() / 14.0) - 1));
        return "trainer/trainer";
    }
}
