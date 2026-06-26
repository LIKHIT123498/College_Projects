package com.likhit.campusconnect_erp.repository;

import com.likhit.campusconnect_erp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository
        extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s JOIN FETCH s.user JOIN FETCH s.department")
    List<Student> findAllWithDetails();

    Optional<Student> findByUserId(Long userId);
}
