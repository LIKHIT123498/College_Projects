package com.likhit.campusconnect_erp.controller;

import com.likhit.campusconnect_erp.dto.LoginRequest;
import com.likhit.campusconnect_erp.entity.User;
import com.likhit.campusconnect_erp.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        System.out.println("EMAIL = " + request.getEmail());
        System.out.println("PASSWORD = " + request.getPassword());

        Optional<User> user = userRepository.findByEmailAndPassword(
                request.getEmail(),
                request.getPassword()
        );

        System.out.println("FOUND USER = " + user);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }

        return ResponseEntity.badRequest()
                .body("Invalid Credentials");
    }

    @GetMapping("/test-user")
    public User testUser() {

        return userRepository
                .findByEmailAndPassword(
                        "john@example.com",
                        "12345"
                )
                .orElseThrow();
    }

    @GetMapping("/all-users")
    public java.util.List<User> allUsers() {
        return userRepository.findAll();
    }
}