package com.likhit.campusconnect_erp.controller;

import com.likhit.campusconnect_erp.entity.Student;
import com.likhit.campusconnect_erp.entity.User;
import com.likhit.campusconnect_erp.repository.*;
import com.likhit.campusconnect_erp.exception.DuplicateEmailException;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentRepository repository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final AttendanceRepository attendanceRepository;
    private final MarksRepository marksRepository;
    private final SubmissionRepository submissionRepository;

    public StudentController(StudentRepository repository,
                             UserRepository userRepository,
                             DepartmentRepository departmentRepository,
                             AttendanceRepository attendanceRepository,
                             MarksRepository marksRepository,
                             SubmissionRepository submissionRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.attendanceRepository = attendanceRepository;
        this.marksRepository = marksRepository;
        this.submissionRepository = submissionRepository;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return repository.findAllWithDetails(); // Use the optimized query
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<Student> getStudentByUserId(@PathVariable Long userId) {
        return repository.findByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public Student createStudent(@RequestBody Student student) {
        Student entity = new Student();
        entity.setUsn(student.getUsn());
        entity.setSemester(student.getSemester());

        if (student.getUser() != null) {
            User incoming = student.getUser();
            if (incoming.getId() != null) {
                userRepository.findById(incoming.getId()).ifPresent(entity::setUser); // Link existing user
            } else {
                if (userRepository.findByEmail(incoming.getEmail()).isPresent()) {
                    throw new DuplicateEmailException("Email '" + incoming.getEmail() + "' is already in use.");
                }
                User newUser = new User();
                newUser.setFullName(incoming.getFullName());
                newUser.setEmail(incoming.getEmail());
                newUser.setRole("STUDENT");
                newUser.setPassword(incoming.getPassword() != null ? incoming.getPassword() : "password");
                entity.setUser(userRepository.save(newUser));
            }
        }

        if (student.getDepartment() != null && student.getDepartment().getId() != null) {
            departmentRepository.findById(student.getDepartment().getId()).ifPresent(entity::setDepartment);
        }

        return repository.save(entity);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student student) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setUsn(student.getUsn());
                    existing.setSemester(student.getSemester());

                    if (student.getUser() != null) {
                        User incoming = student.getUser();
                        User userToUpdate = existing.getUser();

                        // If the student doesn't have an associated user but ID is provided, find it
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

                    if (student.getDepartment() != null && student.getDepartment().getId() != null) {
                        departmentRepository.findById(student.getDepartment().getId()).ifPresent(existing::setDepartment);
                    }

                    return ResponseEntity.ok(repository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        return repository.findById(id)
                .map(existing -> {
                    // Clean up related records first to avoid Foreign Key constraint violations
                    attendanceRepository.findAll().stream()
                            .filter(a -> a.getStudent() != null && a.getStudent().getId().equals(id))
                            .forEach(attendanceRepository::delete);
                    marksRepository.findAll().stream()
                            .filter(m -> m.getStudent() != null && m.getStudent().getId().equals(id))
                            .forEach(marksRepository::delete);
                    submissionRepository.findAll().stream()
                            .filter(s -> s.getStudent() != null && s.getStudent().getId().equals(id))
                            .forEach(submissionRepository::delete);

                    User associatedUser = existing.getUser();
                    repository.delete(existing);
                    if (associatedUser != null) {
                        userRepository.delete(associatedUser);
                    }
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
