package com.luna.slcact4_t4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luna.slcact4_t4.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndStudentIdNot(String email, Long studentId);
}
