package com.likhit.campusconnect_erp.controller;

import com.likhit.campusconnect_erp.entity.Course;
import com.likhit.campusconnect_erp.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*")
public class CourseController {

    private final CourseRepository repository;
    private final TimetableRepository timetableRepository;
    private final AttendanceRepository attendanceRepository;
    private final ExamRepository examRepository;
    private final AssignmentRepository assignmentRepository;

    public CourseController(CourseRepository repository,
                            TimetableRepository timetableRepository,
                            AttendanceRepository attendanceRepository,
                            ExamRepository examRepository,
                            AssignmentRepository assignmentRepository) {
        this.repository = repository;
        this.timetableRepository = timetableRepository;
        this.attendanceRepository = attendanceRepository;
        this.examRepository = examRepository;
        this.assignmentRepository = assignmentRepository;
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return repository.findAll();
    }

    @PostMapping
    public Course createCourse(
            @RequestBody Course course) {

        return repository.save(course);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long id,
            @RequestBody Course course) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setCourseCode(course.getCourseCode());
                    existing.setCourseName(course.getCourseName());
                    existing.setCredits(course.getCredits());
                    existing.setSemester(course.getSemester());
                    Course saved = repository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        return repository.findById(id)
                .map(existing -> {
                    // Clean up related records to avoid FK constraints
                    timetableRepository.findAll().stream()
                            .filter(t -> t.getCourse() != null && t.getCourse().getId().equals(id))
                            .forEach(timetableRepository::delete);

                    attendanceRepository.findAll().stream()
                            .filter(a -> a.getCourse() != null && a.getCourse().getId().equals(id))
                            .forEach(attendanceRepository::delete);

                    examRepository.findAll().stream()
                            .filter(e -> e.getCourse() != null && e.getCourse().getId().equals(id))
                            .forEach(examRepository::delete);

                    assignmentRepository.findAll().stream()
                            .filter(a -> a.getCourse() != null && a.getCourse().getId().equals(id))
                            .forEach(assignmentRepository::delete);

                    repository.delete(existing);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
