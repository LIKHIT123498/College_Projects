package com.likhit.campusconnect_erp.controller;

import com.likhit.campusconnect_erp.entity.Exam;
import com.likhit.campusconnect_erp.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exams")
@CrossOrigin(origins = "*")
public class ExamController {

    private final ExamRepository repository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final MarksRepository marksRepository;

    public ExamController(ExamRepository repository, 
                          CourseRepository courseRepository, 
                          TeacherRepository teacherRepository,
                          MarksRepository marksRepository) {
        this.repository = repository;
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.marksRepository = marksRepository;
    }

    @GetMapping
    public List<Exam> getAllExams() {
        return repository.findAll();
    }

    @PostMapping
    @Transactional
    public Exam createExam(
            @RequestBody Exam exam) {
        Exam entity = new Exam();
        if (exam.getCourse() != null && exam.getCourse().getId() != null) {
            courseRepository.findById(exam.getCourse().getId()).ifPresent(entity::setCourse);
        }
        if (exam.getTeacher() != null && exam.getTeacher().getId() != null) {
            teacherRepository.findById(exam.getTeacher().getId()).ifPresent(entity::setTeacher);
        }
        entity.setExamName(exam.getExamName());
        entity.setMaxMarks(exam.getMaxMarks());
        entity.setExamDate(exam.getExamDate());
        return repository.save(entity);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Exam> updateExam(
            @PathVariable Long id,
            @RequestBody Exam exam) {
        return repository.findById(id)
                .map(existing -> {
                    if (exam.getCourse() != null && exam.getCourse().getId() != null) {
                        courseRepository.findById(exam.getCourse().getId()).ifPresent(existing::setCourse);
                    }
                    if (exam.getTeacher() != null && exam.getTeacher().getId() != null) {
                        teacherRepository.findById(exam.getTeacher().getId()).ifPresent(existing::setTeacher);
                    }
                    existing.setExamName(exam.getExamName());
                    existing.setMaxMarks(exam.getMaxMarks());
                    existing.setExamDate(exam.getExamDate());
                    Exam saved = repository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteExam(@PathVariable Long id) {
        return repository.findById(id)
                .map(existing -> {
                    marksRepository.findAll().stream()
                            .filter(m -> m.getExam() != null && m.getExam().getId().equals(id))
                            .forEach(marksRepository::delete);

                    repository.delete(existing);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}