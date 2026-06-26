package com.likhit.campusconnect_erp.controller;

import com.likhit.campusconnect_erp.entity.StudentCourse;
import com.likhit.campusconnect_erp.repository.StudentCourseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student-courses")
@CrossOrigin("*")
public class StudentCourseController {

    private final StudentCourseRepository repository;

    public StudentCourseController(StudentCourseRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<StudentCourse> getAll() {
        return repository.findAll();
    }
}
