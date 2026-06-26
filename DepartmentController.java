package com.likhit.campusconnect_erp.controller;

import com.likhit.campusconnect_erp.entity.Department;
import com.likhit.campusconnect_erp.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@CrossOrigin(origins = "*")
public class DepartmentController {

    private final DepartmentRepository repository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public DepartmentController(DepartmentRepository repository,
                                StudentRepository studentRepository,
                                TeacherRepository teacherRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    @GetMapping
    public List<Department> getAllDepartments() {
        return repository.findAll();
    }
    @PostMapping
    @Transactional
    public Department createDepartment(@RequestBody Department department) {
        return repository.save(department);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department dept) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setDepartmentName(dept.getDepartmentName());
                    return ResponseEntity.ok(repository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
        return repository.findById(id)
                .map(existing -> {
                    // Dissociate students and teachers from this department
                    studentRepository.findAll().stream()
                            .filter(s -> s.getDepartment() != null && s.getDepartment().getId().equals(id))
                            .forEach(s -> { s.setDepartment(null); studentRepository.save(s); });
                    teacherRepository.findAll().stream()
                            .filter(t -> t.getDepartment() != null && t.getDepartment().getId().equals(id))
                            .forEach(t -> { t.setDepartment(null); teacherRepository.save(t); });

                    repository.delete(existing);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}