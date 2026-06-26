package com.likhit.campusconnect_erp.controller;

import com.likhit.campusconnect_erp.entity.Assignment;
import com.likhit.campusconnect_erp.entity.Student;
import com.likhit.campusconnect_erp.entity.Submission;
import com.likhit.campusconnect_erp.repository.AssignmentRepository;
import com.likhit.campusconnect_erp.repository.StudentRepository;
import com.likhit.campusconnect_erp.repository.SubmissionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/submissions")
@CrossOrigin(origins = "*")
public class SubmissionController {

    private final SubmissionRepository repository;
    private final AssignmentRepository assignmentRepository;
    private final StudentRepository studentRepository;

    public SubmissionController(
            SubmissionRepository repository,
            AssignmentRepository assignmentRepository,
            StudentRepository studentRepository) {
        this.repository = repository;
        this.assignmentRepository = assignmentRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<Submission> getAllSubmissions() {
        return repository.findAll();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, path = "/upload")
    public ResponseEntity<?> uploadSubmission(
            @RequestParam Long assignmentId,
            @RequestParam Long studentId,
            @RequestParam MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is required.");
        }

        if (!"application/pdf".equals(file.getContentType())) {
            return ResponseEntity.badRequest().body("Only PDF files are supported.");
        }

        Optional<Assignment> assignment = assignmentRepository.findById(assignmentId);
        Optional<Student> student = studentRepository.findById(studentId);

        if (assignment.isEmpty() || student.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid assignment or student ID.");
        }

        try {
            Path uploadDir = Path.of("uploads");
            Files.createDirectories(uploadDir);
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileName = System.currentTimeMillis() + "-" + originalFileName;
            Path targetLocation = uploadDir.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Submission submission = new Submission();
            submission.setAssignment(assignment.get());
            submission.setStudent(student.get());
            submission.setFileUrl("/uploads/" + fileName);
            submission.setSubmittedAt(LocalDateTime.now());
            Submission saved = repository.save(submission);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not save file.");
        }
    }
}