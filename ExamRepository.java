package com.likhit.campusconnect_erp.repository;

import com.likhit.campusconnect_erp.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository
        extends JpaRepository<Exam, Long> {
}