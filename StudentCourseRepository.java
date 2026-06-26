package com.likhit.campusconnect_erp.repository;

import com.likhit.campusconnect_erp.entity.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCourseRepository
        extends JpaRepository<StudentCourse, Long> {
}