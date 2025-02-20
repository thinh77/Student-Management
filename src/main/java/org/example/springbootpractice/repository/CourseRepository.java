package org.example.springbootpractice.repository;

import org.example.springbootpractice.entity.Course;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    List<Course> findAllByTrainer_Username(String trainerUsername, Sort createdDate);
}
