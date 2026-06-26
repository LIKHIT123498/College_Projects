package com.likhit.campusconnect_erp.repository;

import com.likhit.campusconnect_erp.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository
        extends JpaRepository<Submission, Long> {
}