package org.example.springbootpractice.dto;

import lombok.Data;

@Data
public class CourseStudentDto {
    private String studentId;
    private String courseId;
    private double practice;
    private double asm1;
    private double asm2;
}
