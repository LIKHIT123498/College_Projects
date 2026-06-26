package com.likhit.campusconnect_erp.controller;

import com.likhit.campusconnect_erp.entity.Marks;
import com.likhit.campusconnect_erp.repository.ExamRepository;
import com.likhit.campusconnect_erp.repository.MarksRepository;
import com.likhit.campusconnect_erp.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marks")
@CrossOrigin(origins = "*")
public class MarksController {

    private final MarksRepository repository;
    private final StudentRepository studentRepository;
    private final ExamRepository examRepository;

    public MarksController(MarksRepository repository, StudentRepository studentRepository, ExamRepository examRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
        this.examRepository = examRepository;
    }

    @GetMapping
    public List<Marks> getAllMarks() {
        return repository.findAll();
    }

    @PostMapping
    public Marks createMarks(
            @RequestBody Marks marks) {
        Marks entity = new Marks();
        if (marks.getStudent() != null && marks.getStudent().getId() != null) {
            studentRepository.findById(marks.getStudent().getId()).ifPresent(entity::setStudent);
        }
        if (marks.getExam() != null && marks.getExam().getId() != null) {
            examRepository.findById(marks.getExam().getId()).ifPresent(entity::setExam);
        }
        entity.setMarksObtained(marks.getMarksObtained());
        return repository.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Marks> updateMarks(
            @PathVariable Long id,
            @RequestBody Marks marks) {
        return repository.findById(id)
                .map(existing -> {
                    if (marks.getStudent() != null && marks.getStudent().getId() != null) {
                        studentRepository.findById(marks.getStudent().getId()).ifPresent(existing::setStudent);
                    }
                    if (marks.getExam() != null && marks.getExam().getId() != null) {
                        examRepository.findById(marks.getExam().getId()).ifPresent(existing::setExam);
                    }
                    existing.setMarksObtained(marks.getMarksObtained());
                    Marks saved = repository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMarks(@PathVariable Long id) {
        return repository.findById(id)
                .map(existing -> {
                    repository.delete(existing);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
