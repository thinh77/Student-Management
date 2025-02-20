package org.example.springbootpractice.dto;

import lombok.Data;

@Data
public class CourseDto {
    private String id;
    private String name;
    private String code;
    private String description;
    private String startDate;
    private String endDate;
    private String className;
    private String trainerName;
}
