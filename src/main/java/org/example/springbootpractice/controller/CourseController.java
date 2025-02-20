package org.example.springbootpractice.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbootpractice.dto.CourseDto;
import org.example.springbootpractice.dto.StudentDto;
import org.example.springbootpractice.dto.requestDto.CreateCourseRq;
import org.example.springbootpractice.entity.CourseStudent;
import org.example.springbootpractice.repository.CourseStudentRepository;
import org.example.springbootpractice.service.impl.AccountServiceImpl;
import org.example.springbootpractice.service.impl.ClassServiceImpl;
import org.example.springbootpractice.service.impl.CourseServiceImpl;
import org.example.springbootpractice.service.impl.StudentServiceImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseServiceImpl courseService;
    private final ClassServiceImpl classService;
    private final AccountServiceImpl accountService;
    private final StudentServiceImpl studentService;
    private final CourseStudentRepository courseStudentRepository;
    private final double pageSize = 3;

    @GetMapping("/create")
    public String createCourse(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("role", authentication.getAuthorities().stream().findFirst().get().getAuthority());
        model.addAttribute("course", new CreateCourseRq());
        model.addAttribute("classes", classService.getAllClasses());
        model.addAttribute("account", accountService.findAllByRole("Trainer"));
        model.addAttribute("trainerUsername", authentication.getName());
        model.addAttribute("lastPage", ((int) Math.ceil(courseService.getNumberOfCourses() / pageSize) - 1));
        return "course/create-course";
    }

    @PostMapping("/create")
    public String createCourse(@ModelAttribute("course") CreateCourseRq createCourseRq, @RequestParam int page, BindingResult bindingResult, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            courseService.createCourse(createCourseRq);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            bindingResult.rejectValue("startDate", "course.startDate.required", "Start date must be before end date");
            model.addAttribute("course", createCourseRq);
            model.addAttribute("classes", classService.getAllClasses());
            model.addAttribute("account", accountService.findAllByRole("Trainer"));
            return "course/create-course";
        }
        if (authentication.getAuthorities().stream().findFirst().get().getAuthority().equals("Trainer")) {
            return "redirect:/trainer/" + authentication.getName();
        }
        model.addAttribute("courses", courseService.findAllCourses(page, 3));
        int numberOfPages = (int) Math.ceil(courseService.getNumberOfCourses() / pageSize);
        model.addAttribute("numberOfPages", numberOfPages);
        return "course/course-list";
    }

    @GetMapping("/admin")
    public String getAllCourses(@RequestParam int page, Model model) {
        model.addAttribute("courses", courseService.findAllCourses(page, 3));
        int numberOfPages = (int) Math.ceil(courseService.getNumberOfCourses() / pageSize);
        model.addAttribute("numberOfPages", numberOfPages);
        model.addAttribute("currentPage", page);
        return "course/course-list";
    }

    @GetMapping("/admin/update/{id}")
    public String updateCourse(@PathVariable("id") String id, @RequestParam int page, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("role", authentication.getAuthorities().stream().findFirst().get().getAuthority());
        model.addAttribute("course", courseService.findCourseById(id));
        model.addAttribute("classes", classService.getAllClasses());
        model.addAttribute("account", accountService.findAllByRole("Trainer"));
        model.addAttribute("trainerUsername", authentication.getName());
        model.addAttribute("currentPage", page);
        return "course/update-course";
    }

    @PostMapping("/admin/update")
    public String updateCourse(@ModelAttribute("course") CourseDto courseDto, @RequestParam int page, BindingResult bindingResult, Model model) {
        try {
            courseService.updateCourse(courseDto);
        } catch (IllegalArgumentException e) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            model.addAttribute("error", e.getMessage());
            bindingResult.rejectValue("startDate", "course.startDate.required", "Start date must be before end date");
            model.addAttribute("role", authentication.getAuthorities().stream().findFirst().get().getAuthority());
            model.addAttribute("course", courseService.findCourseById(courseDto.getId()));
            model.addAttribute("classes", classService.getAllClasses());
            model.addAttribute("account", accountService.findAllByRole("Trainer"));
            model.addAttribute("trainerUsername", authentication.getName());
            return "course/update-course";
        }

        model.addAttribute("courses", courseService.findAllCourses(page, 3));
        int numberOfPages = (int) Math.ceil(courseService.getNumberOfCourses() / pageSize);
        model.addAttribute("numberOfPages", numberOfPages);
        model.addAttribute("currentPage", page);
        return "course/course-list";
    }

    @GetMapping("/admin/add-student/{courseId}")
    public String addStudentToCourse(@PathVariable String courseId, @RequestParam int page, Model model) {
        List<StudentDto> students = studentService.getStudentByClassId(courseId, page);
        model.addAttribute("courseName", courseService.findCourseById(courseId).getName());
        model.addAttribute("courseId", courseId);
        model.addAttribute("students", students);
        Long numberOfPages = studentService.getNumberOfStudentByClass(courseId) / 3;
        model.addAttribute("numberOfPages", numberOfPages);
        model.addAttribute("page", page);
        return "course/add-student-into-course";
    }

    @PostMapping("/admin/add-student/{courseId}")
    public String addStudentToCourse(@PathVariable("courseId") String courseId, @RequestParam("studentIds") List<String> studentIds, Model model) {
        courseService.addStudentToCourse(courseId, studentIds);
        model.addAttribute("courses", courseService.findAllCourses(0, 3));
        return "redirect:/admin";
    }

    @GetMapping("/trainer/{username}")
    public String getAllCoursesByTrainer(@PathVariable("username") String username, Model model) {
        model.addAttribute("courses", courseService.findAllCoursesByTrainerName(username));
        model.addAttribute("page", 0);
        return "trainer/trainer-course-data";
    }


    @GetMapping("/trainer/detail/{courseId}")
    public String getCourseDetail(@PathVariable("courseId") String courseId, @RequestParam int page, Model model) {
        model.addAttribute("courseId", courseId);
        model.addAttribute("courseName", courseService.findCourseById(courseId).getName());
        model.addAttribute("students", studentService.getStudentByCourseId(courseId, page));
        List<CourseStudent> courseStudents = courseStudentRepository.getCourseStudentByCourse_Id(courseId, PageRequest.of(page, 7)).toList();
        model.addAttribute("courseStudents", courseStudents);
        int numberOfPages = (int) Math.ceil(courseStudentRepository.countCourseStudentByCourse_Id(courseId) / 7.0);
        model.addAttribute("numberOfPages", numberOfPages);
        return "trainer/course-detail";
    }

    @PostMapping("/trainer/detail/{courseId}")
    public String updateScore(@PathVariable("courseId") String courseId, @RequestParam("studentId") String studentId,
                              @RequestParam int page,
                              @RequestParam(value = "practiceScore") Double practiceScore, @RequestParam(value = "assignment1Score") double assignment1Score,
                              @RequestParam(value = "assignment2Score") double assignment2Score, Model model) {
        courseService.updateScore(courseId, studentId, practiceScore, assignment1Score, assignment2Score);
        model.addAttribute("courseName", courseService.findCourseById(courseId).getName());
        model.addAttribute("students", studentService.getStudentByCourseId(courseId, page));
        List<CourseStudent> courseStudents = courseStudentRepository.findAllStudentByCourseId(courseId);
        model.addAttribute("courseStudents", courseStudents);
        int numberOfPages = (int) Math.ceil(courseStudents.size() / 7.0);
        model.addAttribute("numberOfPages", numberOfPages);
        return "trainer/course-detail";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable("id") String id, Model model) {
        courseService.deleteCourse(id);
        model.addAttribute("courses", courseService.findAllCourses(0, 3));
        return "course/course-list";
    }
}
