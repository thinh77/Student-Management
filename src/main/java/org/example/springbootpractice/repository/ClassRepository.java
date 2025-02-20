package org.example.springbootpractice.repository;

import org.example.springbootpractice.entity.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Classes, String> {
    Classes findByClassName(String className);

    boolean existsByClassName(String className);
}
