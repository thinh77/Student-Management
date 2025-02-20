package org.example.springbootpractice.service;

import org.example.springbootpractice.dto.ClassDto;
import org.example.springbootpractice.dto.requestDto.CreateClassRq;

import java.util.List;

public interface ClassService {
    void addClass(CreateClassRq createClassRq);
    ClassDto getClassById(String id);
    List<ClassDto> getAllClasses();
    void updateClass(ClassDto classDto);
    void deleteClass(String id);
    void addStudentToClass(String classId, List<String> studentIds);
}
