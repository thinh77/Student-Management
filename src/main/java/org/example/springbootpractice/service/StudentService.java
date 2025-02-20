package org.example.springbootpractice.service;

import org.example.springbootpractice.dto.StudentDto;
import org.example.springbootpractice.dto.requestDto.CreateStudentRq;

import java.util.List;

public interface StudentService {
    void addStudent(CreateStudentRq createStudentRq);
    List<StudentDto> getStudentByPage(int page);
    List<StudentDto> getAllStudents();
    StudentDto getStudentById(String id);
    void updateStudent(StudentDto studentResponseDto);
    void deleteStudent(String id);
    List<StudentDto> getStudentByCourseId(String courseId, int page);
    List<StudentDto> getStudentByClassId(String classId, int page);
}
