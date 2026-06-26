package com.likhit.campusconnect_erp.repository;

import com.likhit.campusconnect_erp.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository
        extends JpaRepository<Department, Long> {
}