package com.likhit.campusconnect_erp.controller;

import com.likhit.campusconnect_erp.entity.Timetable;
import com.likhit.campusconnect_erp.repository.CourseRepository;
import com.likhit.campusconnect_erp.repository.TeacherRepository;
import com.likhit.campusconnect_erp.repository.TimetableRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timetable")
@CrossOrigin(origins = "*")
public class TimetableController {

    private final TimetableRepository repository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    public TimetableController(
            TimetableRepository repository,
            CourseRepository courseRepository,
            TeacherRepository teacherRepository) {
        this.repository = repository;
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
    }

    @GetMapping
    public List<Timetable> getAllTimetable() {
        return repository.findAll();
    }

    @PostMapping
    @Transactional
    public Timetable createTimetable(@RequestBody Timetable timetable) {
        Timetable entity = new Timetable();
        applyTimetableFields(entity, timetable);
        return repository.save(entity);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Timetable> updateTimetable(
            @PathVariable Long id,
            @RequestBody Timetable timetable) {
        return repository.findById(id)
                .map(existing -> {
                    applyTimetableFields(existing, timetable);
                    Timetable saved = repository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteTimetable(@PathVariable Long id) {
        return repository.findById(id)
                .map(existing -> {
                    repository.delete(existing);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private void applyTimetableFields(Timetable entity, Timetable source) {
        if (source.getCourse() != null && source.getCourse().getId() != null) {
            courseRepository.findById(source.getCourse().getId()).ifPresent(entity::setCourse);
        }
        if (source.getTeacher() != null && source.getTeacher().getId() != null) {
            teacherRepository.findById(source.getTeacher().getId()).ifPresent(entity::setTeacher);
        }
        if (source.getDayOfWeek() != null) {
            entity.setDayOfWeek(source.getDayOfWeek());
        }
        if (source.getStartTime() != null) {
            entity.setStartTime(source.getStartTime());
        }
        if (source.getEndTime() != null) {
            entity.setEndTime(source.getEndTime());
        }
        if (source.getRoomNumber() != null) {
            entity.setRoomNumber(source.getRoomNumber());
        }
    }
}
