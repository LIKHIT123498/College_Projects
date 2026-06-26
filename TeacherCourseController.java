package com.likhit.campusconnect_erp.controller;

import com.likhit.campusconnect_erp.entity.TeacherCourse;
import com.likhit.campusconnect_erp.repository.TeacherCourseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher-courses")
@CrossOrigin("*")
public class TeacherCourseController {

    private final TeacherCourseRepository repository;

    public TeacherCourseController(TeacherCourseRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<TeacherCourse> getAll() {
        return repository.findAll();
    }
}