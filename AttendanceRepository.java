package com.likhit.campusconnect_erp.repository;

import com.likhit.campusconnect_erp.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository
        extends JpaRepository<Attendance, Long> {
}