package com.example.studentmanager.controller;

import com.example.studentmanager.dto.Dtos.*;
import com.example.studentmanager.model.User;
import com.example.studentmanager.repository.UserRepository;
import com.example.studentmanager.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired private UserRepository  userRepository;
    @Autowired private JwtService      jwtService;
    @Autowired private PasswordEncoder passwordEncoder;

    /**
     * POST /login
     * Body: { "email": "...", "password": "..." }
     * Returns: { "token": "...", "role": "ADMIN", "email": "..." }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        User user = userRepository.findByEmail(req.email)
                .orElse(null);

        if (user == null || !passwordEncoder.matches(req.password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Invalid email or password"));
        }

        String token = jwtService.generateToken(user.getEmail(), user.getRole());
        return ResponseEntity.ok(new LoginResponse(token, user.getRole(), user.getEmail()));
    }
}
