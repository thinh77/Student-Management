package org.example.springbootpractice.repository;

import org.example.springbootpractice.entity.Classes;
import org.example.springbootpractice.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    boolean existsByIdCardNumber(String idCardNumber);

    Page<Student> getAllByClasses(Classes classes, Pageable pageable);


    Long countStudentByClasses(Classes classes);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    double countByEmail(String email);

    double countByPhone(String phone);

    Object getStudentsById(String id);
}