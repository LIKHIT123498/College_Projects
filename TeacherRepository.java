package com.likhit.campusconnect_erp.repository;

import com.likhit.campusconnect_erp.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository
        extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByUserId(Long userId);
}