package org.example.springbootpractice.repository;

import org.example.springbootpractice.entity.CourseStudent;
import org.example.springbootpractice.entity.StudentCourseId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseStudentRepository extends JpaRepository<CourseStudent, StudentCourseId> {
    boolean existsById(StudentCourseId id);
    List<CourseStudent> findAllStudentByCourseId(String courseId);

    Page<CourseStudent> getCourseStudentByCourse_Id(String courseId, Pageable pageable);

    double getCourseStudentByCourse_Id(String courseId);

    double countCourseStudentByCourse_Id(String courseId);
}
