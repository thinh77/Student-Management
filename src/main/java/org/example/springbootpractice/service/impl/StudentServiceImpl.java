package org.example.springbootpractice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.springbootpractice.dto.StudentDto;
import org.example.springbootpractice.dto.requestDto.CreateStudentRq;
import org.example.springbootpractice.entity.Classes;
import org.example.springbootpractice.entity.Course;
import org.example.springbootpractice.entity.CourseStudent;
import org.example.springbootpractice.entity.Student;
import org.example.springbootpractice.exception.DuplicateEmailException;
import org.example.springbootpractice.exception.DuplicateIdCardNumberException;
import org.example.springbootpractice.exception.DuplicatePhoneException;
import org.example.springbootpractice.repository.ClassRepository;
import org.example.springbootpractice.repository.CourseRepository;
import org.example.springbootpractice.repository.CourseStudentRepository;
import org.example.springbootpractice.repository.StudentRepository;
import org.example.springbootpractice.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    private final CourseStudentRepository courseStudentRepository;

    @Override
    public void addStudent(CreateStudentRq createStudentRq) throws DataIntegrityViolationException {
        Classes classes = this.classRepository.findByClassName(createStudentRq.getClassName());
        Student student = this.modelMapper.map(createStudentRq, Student.class);
        student.setClasses(classes);
        this.studentRepository.save(student);
    }

    @Override
    public List<StudentDto> getStudentByPage(int page) {
        Page<Student> students = this.studentRepository.findAll(PageRequest.of(page,14,Sort.by("createdDate")));
        return students.stream()
                .map(student -> {
            StudentDto studentResponseDto = this.modelMapper.map(student, StudentDto.class);
            if (student.getClasses() != null) {
                studentResponseDto.setClassName(student.getClasses().getClassName());
            } else {
                studentResponseDto.setClassName("No class");
            }
            return studentResponseDto;
        }).toList();
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = this.studentRepository.findAll(Sort.by("createdDate"));
        return students.stream()
                .map(student -> {
            StudentDto studentResponseDto = this.modelMapper.map(student, StudentDto.class);
            if (student.getClasses() != null) {
                studentResponseDto.setClassName(student.getClasses().getClassName());
            } else {
                studentResponseDto.setClassName("No class");
            }
            return studentResponseDto;
        }).toList();
    }

    @Override
    public StudentDto getStudentById(String id) {
        Student student = this.studentRepository.findById(id).orElseThrow();
        StudentDto studentResponseDto = this.modelMapper.map(student, StudentDto.class);
        if (student.getClasses() != null) {
            studentResponseDto.setClassName(student.getClasses().getClassName());
        } else {
            studentResponseDto.setClassName("No class");
        }
        return studentResponseDto;
    }

    @Override
    public void updateStudent(StudentDto studentDto) {
        Student existingStudent = studentRepository.findById(studentDto.getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        if (existingStudent.getAvatarUrl() != null && studentDto.getAvatarUrl() == null) {
            studentDto.setAvatarUrl(existingStudent.getAvatarUrl());
        }
        if (existingStudent.getDegreeUrl() != null && studentDto.getDegreeUrl() == null) {
            studentDto.setDegreeUrl(existingStudent.getDegreeUrl());
        }
        if (!studentDto.getEmail().equals(existingStudent.getEmail())) {
            if (studentRepository.existsByEmail((studentDto.getEmail()))) {
                throw new DuplicateEmailException("Email already exists");
            }
        }
        if (!studentDto.getPhone().equals(existingStudent.getPhone())) {
            if (studentRepository.existsByPhone(studentDto.getPhone())) {
                throw new DuplicatePhoneException("Phone already exists");
            }
        }
        if (!studentDto.getIdCardNumber().equals(existingStudent.getIdCardNumber())) {
            if (studentRepository.existsByIdCardNumber(studentDto.getIdCardNumber())) {
                throw new DuplicateIdCardNumberException("Id card number already exists");
            }
        }
        Classes classes = this.classRepository.findByClassName(studentDto.getClassName());
        this.modelMapper.map(studentDto, existingStudent);
        existingStudent.setClasses(classes);
        this.studentRepository.save(existingStudent);
    }

    @Override
    public void deleteStudent(String id) {
        Path avatarPath = Path.of("src/main/resources/static" + studentRepository.getReferenceById(id).getAvatarUrl());
        Path degreePath = Path.of("src/main/resources/static" + studentRepository.getReferenceById(id).getDegreeUrl());
        try {
            java.nio.file.Files.deleteIfExists(avatarPath);
            java.nio.file.Files.deleteIfExists(degreePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.studentRepository.deleteById(id);
    }

    @Override
    public List<StudentDto> getStudentByCourseId(String courseId, int page) {
        Page<CourseStudent> courseStudents = courseStudentRepository.getCourseStudentByCourse_Id(courseId, PageRequest.of(page, 7));
        return courseStudents.stream().map(courseStudent -> {
            Student student = courseStudent.getStudent();
            StudentDto studentResponseDto = this.modelMapper.map(student, StudentDto.class);
            studentResponseDto.setClassName(student.getClasses().getClassName());
            return studentResponseDto;
        }).toList();
    }

    @Override
    public List<StudentDto> getStudentByClassId(String courseId, int page) {
        Course course = courseRepository.findById(courseId).orElseThrow();
        Classes classes = course.getClassRoom();
        Page<Student> students = studentRepository.getAllByClasses(classes, PageRequest.of(page, 14));
        return students.stream().map(student -> {
            StudentDto studentResponseDto = this.modelMapper.map(student, StudentDto.class);
            studentResponseDto.setClassName(student.getClasses().getClassName());
            return studentResponseDto;
        }).toList();
    }

    public Long getNumberOfStudentByClass(String courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow();
        Classes classes = course.getClassRoom();
        return studentRepository.countStudentByClasses(classes);
    }
}
