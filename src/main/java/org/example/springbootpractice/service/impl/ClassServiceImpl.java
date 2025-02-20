package org.example.springbootpractice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.springbootpractice.dto.ClassDto;
import org.example.springbootpractice.dto.requestDto.CreateClassRq;
import org.example.springbootpractice.entity.Classes;
import org.example.springbootpractice.entity.Student;
import org.example.springbootpractice.repository.ClassRepository;
import org.example.springbootpractice.repository.StudentRepository;
import org.example.springbootpractice.service.ClassService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {
    private final ClassRepository classRepository;
    private final ModelMapper modelMapper;
    private final StudentRepository studentRepository;

    @Override
    public void addClass(CreateClassRq createClassRq) {
        Classes classes = this.modelMapper.map(createClassRq, Classes.class);
        this.classRepository.save(classes);
    }

    @Override
    public ClassDto getClassById(String id) {
        Classes classes = this.classRepository.findById(id).orElseThrow();
        return this.modelMapper.map(classes, ClassDto.class);
    }

    @Override
    public List<ClassDto> getAllClasses() {
        List<Classes> classes = this.classRepository.findAll(Sort.by("createdDate"));
        return classes.stream().map(classes1 -> this.modelMapper.map(classes1, ClassDto.class)).toList();
    }

    public List<ClassDto> getClassesByPage(int page, int size) {
        Page<Classes> classes = this.classRepository.findAll(PageRequest.of(page, size, Sort.by("createdDate")));
        return classes.stream().map(classes1 -> this.modelMapper.map(classes1, ClassDto.class)).toList();
    }

    @Override
    public void updateClass(ClassDto classDto) {
        Classes classes = this.classRepository.findById(classDto.getId()).orElseThrow();
        this.modelMapper.map(classDto, classes);
        this.classRepository.save(classes);
    }

    @Override
    public void addStudentToClass(String classId, List<String> studentIds) {
        Classes classes = this.classRepository.findById(classId).orElseThrow();
        List<Student> student = studentRepository.findAllById(studentIds);
        student.forEach(student1 -> student1.setClasses(classes));
        this.studentRepository.saveAll(student);
    }

    @Override
    public void deleteClass(String id) {
        this.classRepository.deleteById(id);
    }
}
