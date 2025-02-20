package org.example.springbootpractice.service;

import org.example.springbootpractice.dto.CourseDto;
import org.example.springbootpractice.dto.requestDto.CreateCourseRq;

import java.util.List;

public interface CourseService {
    void createCourse(CreateCourseRq createCourseRq);
    CourseDto findCourseById(String id);
    List<CourseDto> findAllCourses(int page, int size);
    List<CourseDto> findAllCoursesByTrainerName(String trainerName);
    void addStudentToCourse(String courseId, List<String> studentIds);
    void updateScore(String courseId, String studentId, double practiceScore, double assignment1Score, double assignment2Score);
    void updateCourse(CourseDto courseDto);
    void deleteCourse(String id);
}
