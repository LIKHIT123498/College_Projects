package com.likhit.campusconnect_erp.repository;

import com.likhit.campusconnect_erp.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository
        extends JpaRepository<Course, Long> {
}