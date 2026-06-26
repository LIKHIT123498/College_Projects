package com.likhit.campusconnect_erp.controller;

import com.likhit.campusconnect_erp.entity.Assignment;
import com.likhit.campusconnect_erp.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assignments")
@CrossOrigin(origins = "*")
public class AssignmentController {

    private final AssignmentRepository repository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final SubmissionRepository submissionRepository;

    public AssignmentController(
            AssignmentRepository repository,
            CourseRepository courseRepository,
            TeacherRepository teacherRepository,
            SubmissionRepository submissionRepository) {
        this.repository = repository;
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.submissionRepository = submissionRepository;
    }

    @GetMapping
    public List<Assignment> getAllAssignments() {
        return repository.findAll();
    }

    @PostMapping
    @Transactional
    public Assignment createAssignment(
            @RequestBody Assignment assignment) {
        Assignment entity = new Assignment();
        if (assignment.getCourse() != null && assignment.getCourse().getId() != null) {
            courseRepository.findById(assignment.getCourse().getId()).ifPresent(entity::setCourse);
        }
        if (assignment.getTeacher() != null && assignment.getTeacher().getId() != null) {
            teacherRepository.findById(assignment.getTeacher().getId()).ifPresent(entity::setTeacher);
        }
        entity.setTitle(assignment.getTitle());
        entity.setDescription(assignment.getDescription());
        entity.setDueDate(assignment.getDueDate());
        return repository.save(entity);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Assignment> updateAssignment(
            @PathVariable Long id,
            @RequestBody Assignment assignment) {
        return repository.findById(id)
                .map(existing -> {
                    if (assignment.getCourse() != null && assignment.getCourse().getId() != null) {
                        courseRepository.findById(assignment.getCourse().getId()).ifPresent(existing::setCourse);
                    }
                    if (assignment.getTeacher() != null && assignment.getTeacher().getId() != null) {
                        teacherRepository.findById(assignment.getTeacher().getId()).ifPresent(existing::setTeacher);
                    }
                    existing.setTitle(assignment.getTitle());
                    existing.setDescription(assignment.getDescription());
                    existing.setDueDate(assignment.getDueDate());
                    Assignment saved = repository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteAssignment(@PathVariable Long id) {
        return repository.findById(id)
                .map(existing -> {
                    submissionRepository.findAll().stream()
                            .filter(s -> s.getAssignment() != null && s.getAssignment().getId().equals(id))
                            .forEach(submissionRepository::delete);

                    repository.delete(existing);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}