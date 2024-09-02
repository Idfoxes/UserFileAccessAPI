package com.task.spring.controller;

import com.task.spring.model.User;
import com.task.spring.service.S3Service;
import com.task.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class ApiController {
    private final UserService userService;
    private final S3Service s3Service;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        userService.register(user);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        String token = userService.authenticate(email, password);
        if (token != null) {
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @GetMapping("/generate-upload-url")
    public ResponseEntity<String> generateUploadUrl(@RequestParam String fileName) {
        String url = s3Service.generateUploadUrl(fileName);
        return ResponseEntity.ok(url);
    }

    @GetMapping("/generate-download-url")
    public ResponseEntity<String> generateDownloadUrl(@RequestParam String fileName) {
        String url = s3Service.generateDownloadUrl(fileName);
        return ResponseEntity.ok(url);
    }
}