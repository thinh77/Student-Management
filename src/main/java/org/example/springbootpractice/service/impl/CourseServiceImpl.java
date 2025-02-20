package org.example.springbootpractice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.springbootpractice.dto.CourseDto;
import org.example.springbootpractice.dto.requestDto.CreateCourseRq;
import org.example.springbootpractice.entity.Course;
import org.example.springbootpractice.entity.CourseStudent;
import org.example.springbootpractice.entity.Student;
import org.example.springbootpractice.entity.StudentCourseId;
import org.example.springbootpractice.repository.*;
import org.example.springbootpractice.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final ClassRepository classRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final StudentRepository studentRepository;
    private final CourseStudentRepository courseStudentRepository;

    @Override
    public void createCourse(CreateCourseRq createCourseRq) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(createCourseRq.getStartDate(), formatter);
        LocalDate endDate = LocalDate.parse(createCourseRq.getEndDate(), formatter);

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        Course course = modelMapper.map(createCourseRq, Course.class);
        course.setClassRoom(classRepository.findByClassName(createCourseRq.getClassName()));
        course.setTrainer(accountRepository.findByUsername(createCourseRq.getTrainerName()).orElseThrow());
        courseRepository.save(course);
    }

    @Override
    public CourseDto findCourseById(String id) {
        Optional<Course> course = courseRepository.findById(id);
        CourseDto courseDto = modelMapper.map(course.orElseThrow(), CourseDto.class);
        courseDto.setClassName(course.get().getClassRoom().getClassName());
        courseDto.setTrainerName(course.get().getTrainer().getUsername());
        return courseDto;
    }

    @Override
    public List<CourseDto> findAllCourses(int page, int size) {
        Page<Course> courses = courseRepository.findAll(PageRequest.of(page, size, Sort.by("createdDate")));
        return courses
                .stream()
                .map(course -> {
                    CourseDto courseDto = modelMapper.map(course, CourseDto.class);
                    courseDto.setClassName(course.getClassRoom().getClassName());
                    courseDto.setTrainerName(course.getTrainer().getUsername());
                    return courseDto;
                }).toList();
    }

    @Override
    public List<CourseDto> findAllCoursesByTrainerName(String trainerName) {
        return courseRepository.findAllByTrainer_Username(trainerName,Sort.by("createdDate"))
                .stream()
                .map(course -> {
                    CourseDto courseDto = modelMapper.map(course, CourseDto.class);
                    courseDto.setClassName(course.getClassRoom().getClassName());
                    courseDto.setTrainerName(course.getTrainer().getUsername());
                    return courseDto;
                }).toList();
    }

    @Override
    public void addStudentToCourse(String courseId, List<String> studentIds) {
        Course course = courseRepository.findById(courseId).orElseThrow();
        studentIds.forEach(studentId -> {
            Student student = studentRepository.findById(studentId).orElseThrow();
            StudentCourseId id = new StudentCourseId(student.getId(), course.getId());
            if (!courseStudentRepository.existsById(id)) {
                CourseStudent courseStudent = new CourseStudent();
                courseStudent.setId(id);
                courseStudent.setCourse(course);
                courseStudent.setStudent(student);
                courseStudentRepository.save(courseStudent);
            }
        });
    }

    @Override
    public void updateScore(String courseId, String studentId, double practiceScore, double assignment1Score, double assignment2Score) {
        CourseStudent courseStudent = courseStudentRepository.findById(new StudentCourseId(studentId, courseId)).orElseThrow();
        courseStudent.setPractice(practiceScore);
        courseStudent.setAsm1(assignment1Score);
        courseStudent.setAsm2(assignment2Score);
        courseStudentRepository.save(courseStudent);
    }

    @Override
    public void updateCourse(CourseDto courseDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(courseDto.getStartDate(), formatter);
        LocalDate endDate = LocalDate.parse(courseDto.getEndDate(), formatter);

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        Course course = courseRepository.findById(courseDto.getId()).orElseThrow();
        this.modelMapper.map(courseDto, course);
        course.setClassRoom(classRepository.findByClassName(courseDto.getClassName()));
        course.setTrainer(accountRepository.findByUsername(courseDto.getTrainerName()).orElseThrow());
        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(String id) {
        courseRepository.deleteById(id);
    }

    public Long getNumberOfCourses() {
        return courseRepository.count();
    }
}
