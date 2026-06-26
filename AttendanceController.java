package com.likhit.campusconnect_erp.controller;

import com.likhit.campusconnect_erp.entity.Attendance;
import com.likhit.campusconnect_erp.repository.AttendanceRepository;
import com.likhit.campusconnect_erp.repository.CourseRepository;
import com.likhit.campusconnect_erp.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    private final AttendanceRepository repository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public AttendanceController(
            AttendanceRepository repository,
            StudentRepository studentRepository,
            CourseRepository courseRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<Attendance> getAllAttendance() {
        return repository.findAll();
    }

    @PostMapping
    public Attendance createAttendance(
            @RequestBody Attendance attendance) {
        Attendance entity = new Attendance();
        if (attendance.getStudent() != null && attendance.getStudent().getId() != null) {
            studentRepository.findById(attendance.getStudent().getId()).ifPresent(entity::setStudent);
        }
        if (attendance.getCourse() != null && attendance.getCourse().getId() != null) {
            courseRepository.findById(attendance.getCourse().getId()).ifPresent(entity::setCourse);
        }
        entity.setAttendanceDate(attendance.getAttendanceDate());
        entity.setStatus(attendance.getStatus());
        return repository.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attendance> updateAttendance(
            @PathVariable Long id,
            @RequestBody Attendance attendance) {
        return repository.findById(id)
                .map(existing -> {
                    if (attendance.getStudent() != null && attendance.getStudent().getId() != null) {
                        studentRepository.findById(attendance.getStudent().getId()).ifPresent(existing::setStudent);
                    }
                    if (attendance.getCourse() != null && attendance.getCourse().getId() != null) {
                        courseRepository.findById(attendance.getCourse().getId()).ifPresent(existing::setCourse);
                    }
                    existing.setAttendanceDate(attendance.getAttendanceDate());
                    existing.setStatus(attendance.getStatus());
                    Attendance saved = repository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAttendance(@PathVariable Long id) {
        return repository.findById(id)
                .map(existing -> {
                    repository.delete(existing);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}