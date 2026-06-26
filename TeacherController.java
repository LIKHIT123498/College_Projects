package com.likhit.campusconnect_erp.controller;

import com.likhit.campusconnect_erp.entity.Teacher;
import com.likhit.campusconnect_erp.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
@CrossOrigin(origins = "*")
public class TeacherController {

    private final TeacherRepository repository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final TimetableRepository timetableRepository;
    private final ExamRepository examRepository;
    private final AssignmentRepository assignmentRepository;
    private final MarksRepository marksRepository;
    private final SubmissionRepository submissionRepository;

    public TeacherController(TeacherRepository repository,
                             UserRepository userRepository,
                             DepartmentRepository departmentRepository,
                             TimetableRepository timetableRepository,
                             ExamRepository examRepository,
                             AssignmentRepository assignmentRepository,
                             MarksRepository marksRepository,
                             SubmissionRepository submissionRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.timetableRepository = timetableRepository;
        this.examRepository = examRepository;
        this.assignmentRepository = assignmentRepository;
        this.marksRepository = marksRepository;
        this.submissionRepository = submissionRepository;
    }

    @GetMapping
    public List<Teacher> getAllTeachers() {
        return repository.findAll();
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<Teacher> getTeacherByUserId(@PathVariable Long userId) {
        return repository.findByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        if (teacher.getUser() != null && teacher.getUser().getId() != null) {
            userRepository.findById(teacher.getUser().getId()).ifPresent(teacher::setUser);
        }
        if (teacher.getDepartment() != null && teacher.getDepartment().getId() != null) {
            departmentRepository.findById(teacher.getDepartment().getId()).ifPresent(teacher::setDepartment);
        }
        return repository.save(teacher);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Teacher> updateTeacher(@PathVariable Long id, @RequestBody Teacher teacher) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setEmployeeCode(teacher.getEmployeeCode());
                    existing.setDesignation(teacher.getDesignation());

                    if (teacher.getUser() != null) {
                        com.likhit.campusconnect_erp.entity.User incoming = teacher.getUser();
                        com.likhit.campusconnect_erp.entity.User userToUpdate = existing.getUser();

                        if (userToUpdate == null && incoming.getId() != null) {
                            userToUpdate = userRepository.findById(incoming.getId()).orElse(null);
                        }

                        if (userToUpdate != null) {
                            if (incoming.getFullName() != null) {
                                userToUpdate.setFullName(incoming.getFullName());
                            }
                            if (incoming.getEmail() != null) {
                                userToUpdate.setEmail(incoming.getEmail());
                            }
                            existing.setUser(userRepository.save(userToUpdate));
                        }
                    }
                    if (teacher.getDepartment() != null && teacher.getDepartment().getId() != null) {
                        departmentRepository.findById(teacher.getDepartment().getId()).ifPresent(existing::setDepartment);
                    }
                    return ResponseEntity.ok(repository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteTeacher(@PathVariable Long id) {
        return repository.findById(id)
                .map(existing -> {
                    // Cleanup Timetable entries
                    timetableRepository.findAll().stream()
                            .filter(t -> t.getTeacher() != null && t.getTeacher().getId().equals(id))
                            .forEach(timetableRepository::delete);

                    // Cleanup Exams and related Marks
                    examRepository.findAll().stream()
                            .filter(e -> e.getTeacher() != null && e.getTeacher().getId().equals(id))
                            .forEach(e -> {
                                marksRepository.findAll().stream()
                                        .filter(m -> m.getExam() != null && m.getExam().getId().equals(e.getId()))
                                        .forEach(marksRepository::delete);
                                examRepository.delete(e);
                            });

                    // Cleanup Assignments and related Submissions
                    assignmentRepository.findAll().stream()
                            .filter(a -> a.getTeacher() != null && a.getTeacher().getId().equals(id))
                            .forEach(a -> {
                                submissionRepository.findAll().stream()
                                        .filter(s -> s.getAssignment() != null && s.getAssignment().getId().equals(a.getId()))
                                        .forEach(submissionRepository::delete);
                                assignmentRepository.delete(a);
                            });

                    com.likhit.campusconnect_erp.entity.User associatedUser = existing.getUser();
                    repository.delete(existing);
                    if (associatedUser != null) {
                        userRepository.delete(associatedUser);
                    }
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
