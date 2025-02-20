package org.example.springbootpractice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springbootpractice.dto.StudentDto;
import org.example.springbootpractice.dto.requestDto.CreateStudentRq;
import org.example.springbootpractice.exception.DuplicateEmailException;
import org.example.springbootpractice.exception.DuplicateIdCardNumberException;
import org.example.springbootpractice.exception.DuplicatePhoneException;
import org.example.springbootpractice.service.impl.ClassServiceImpl;
import org.example.springbootpractice.service.impl.StudentServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/student")
public class StudentController {
    private final StudentServiceImpl studentService;
    private final ClassServiceImpl classService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new CreateStudentRq());
        model.addAttribute("classes", classService.getAllClasses());
        return "student/create-student";
    }

    @PostMapping("/create")
    public String createStudent(@ModelAttribute("student") @Valid CreateStudentRq CreateStudentRq, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("classes", classService.getAllClasses());
            return "student/create-student";
        }

        studentService.addStudent(CreateStudentRq);
        int numberOfPages = (int) Math.ceil(studentService.getAllStudents().size() / 14.0);
        model.addAttribute("numberOfPages", numberOfPages);
        model.addAttribute("students", studentService.getStudentByPage(numberOfPages - 1));
        model.addAttribute("page", numberOfPages - 1);
        return "student/student-list";
    }

    @GetMapping("/")
    public String getAllStudents(@RequestParam int page, Model model) {
        List<StudentDto> studentDto = studentService.getStudentByPage(page);
        int numberOfPages = (int) Math.ceil(studentService.getAllStudents().size() / 14.0);
        model.addAttribute("students", studentDto);
        model.addAttribute("numberOfPages", numberOfPages);
        model.addAttribute("page", page);

        return "student/student-list";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") String id, @RequestParam int page, Model model) {
        StudentDto studentDto = studentService.getStudentById(id);
        model.addAttribute("student", studentDto);
        model.addAttribute("classes", classService.getAllClasses());
        model.addAttribute("page", page);
        return "student/student-detail";
    }

    @PostMapping("/edit")
    public String updateStudent(@RequestParam int page,
                                @ModelAttribute("student") @Valid StudentDto studentDto,
                                BindingResult result,
                                Model model,
                                @RequestParam(value = "avatar", required = false) MultipartFile avatarFile,
                                @RequestParam(value = "degree", required = false) MultipartFile degreeFile) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("classes", classService.getAllClasses());
            model.addAttribute("student", studentDto);
            model.addAttribute("page", page);
            return "student/student-detail";
        }
        if (!avatarFile.isEmpty()) {
            String avatarPath = saveFile(avatarFile, "avatar");
            studentDto.setAvatarUrl("/upload/avatar/" + avatarPath);
        }

        if (!degreeFile.isEmpty()) {
            String degreePath = saveFile(degreeFile, "degree");
            studentDto.setDegreeUrl("/upload/degree/" + degreePath);
        }
        try {
            studentService.updateStudent(studentDto);
        } catch (DuplicateEmailException e) {
            result.rejectValue("email", "email.duplicate", e.getMessage());
            model.addAttribute("classes", classService.getAllClasses());
            model.addAttribute("student", studentDto);
            model.addAttribute("page", page);
            return "student/student-detail";
        } catch (DuplicatePhoneException e) {
            result.rejectValue("phone", "phone.duplicate", e.getMessage());
            model.addAttribute("classes", classService.getAllClasses());
            model.addAttribute("student", studentDto);
            model.addAttribute("page", page);
            return "student/student-detail";
        } catch (DuplicateIdCardNumberException e) {
            result.rejectValue("idCardNumber", "idCardNumber.duplicate", e.getMessage());
            model.addAttribute("classes", classService.getAllClasses());
            model.addAttribute("student", studentDto);
            model.addAttribute("page", page);
            return "student/student-detail";
        }
        model.addAttribute("students", studentService.getStudentByPage(page));
        int numberOfPages = (int) Math.ceil(studentService.getAllStudents().size() / 14.0);
        model.addAttribute("numberOfPages", numberOfPages);
        model.addAttribute("page", page);
        return "student/student-list";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") String id, @RequestParam int page, Model model) {
        studentService.deleteStudent(id);
        int numberOfPages = (int) Math.ceil(studentService.getAllStudents().size() / 14.0);
        model.addAttribute("numberOfPages", numberOfPages);
        model.addAttribute("students", studentService.getStudentByPage(page));
        model.addAttribute("page", page);
        return "student/student-list";
    }

    private String saveFile(MultipartFile file, String type) throws IOException {
        String UPLOAD_DIR;
        if (Objects.equals(type, "avatar")) {
            UPLOAD_DIR = "src/main/resources/static/upload/avatar/";
        } else {
            UPLOAD_DIR = "src/main/resources/static/upload/degree/";
        }
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String fileName = type + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }
}
