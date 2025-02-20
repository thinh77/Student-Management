package org.example.springbootpractice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springbootpractice.dto.ClassDto;
import org.example.springbootpractice.dto.requestDto.CreateClassRq;
import org.example.springbootpractice.service.impl.ClassServiceImpl;
import org.example.springbootpractice.service.impl.StudentServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ClassController {
    private final ClassServiceImpl classService;
    private final StudentServiceImpl studentService;

    @GetMapping(value = "/admin/class/create")
    public String addClass(Model model) {
        model.addAttribute("class", new CreateClassRq());
        return "class/create-class";
    }

    @PostMapping("/admin/class/create")
    public String addClass(@ModelAttribute("class") @Valid CreateClassRq createClassRq, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "class/create-class";
        }
        classService.addClass(createClassRq);
        int numberOfPages = (int) Math.ceil(classService.getAllClasses().size() / 14.0);
        model.addAttribute("numberOfPages", numberOfPages);
        return getAllClasses(numberOfPages - 1, model);
    }

    @GetMapping(value = "/admin/class")
    public String getAllClasses(@RequestParam int page, Model model) {
        List<ClassDto> classDto = classService.getClassesByPage(page, 14);
        int numberOfPages = (int) Math.ceil(classService.getAllClasses().size() / 14.0);
        model.addAttribute("numberOfPages", numberOfPages);
        model.addAttribute("classes", classDto);
        model.addAttribute("currentPage", page);
        return "class/class-list";
    }

    @GetMapping(value = "/admin/class/edit/{id}")
    public String editClass(@PathVariable String id, @RequestParam int page, Model model) {
        ClassDto classDto = classService.getClassById(id);
        model.addAttribute("class", classDto);
        model.addAttribute("currentPage", page);
        return "class/update-form";
    }

    @PostMapping(value = "/admin/class/edit")
    public String updateClass( @ModelAttribute("class") ClassDto classDto, @RequestParam int page, Model model) {
        classService.updateClass(classDto);
        int numberOfPages = (int) Math.ceil(classService.getAllClasses().size() / 14.0);
        model.addAttribute("numberOfPages", numberOfPages);
        return getAllClasses(page, model);
    }

    @GetMapping(value = "/admin/class/add-student/{id}")
    public String addStudentToClass(@PathVariable String id, @RequestParam int page, Model model) {
        model.addAttribute("classId", id);
        model.addAttribute("className", classService.getClassById(id).getClassName());
        model.addAttribute("students", studentService.getStudentByPage(0));
        int numberOfPages = (int) Math.ceil(studentService.getAllStudents().size() / 14.0);
        model.addAttribute("numberOfPages", numberOfPages);
        model.addAttribute("currentPage", page);
        return "class/add-students-to-class";
    }

    @PostMapping(value = "/admin/class/add-student")
    public String addStudentToClass(@RequestParam("classId") String classId, @RequestParam("studentIds") List<String> studentIds, @RequestParam int page, Model model) {
        classService.addStudentToClass(classId, studentIds);
        return getAllClasses(page, model);
    }

    @GetMapping(value = "/admin/class/delete/{id}")
    public String deleteClass(@PathVariable String id, Model model) {
        classService.deleteClass(id);
        return getAllClasses(0, model);
    }
}
