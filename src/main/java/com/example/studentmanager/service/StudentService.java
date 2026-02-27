package com.example.studentmanager.service;

import com.example.studentmanager.dto.Dtos.*;
import com.example.studentmanager.model.User;
import com.example.studentmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StudentService {

    @Autowired private UserRepository  userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    // ── Register (public) ─────────────────────────────────────────────────────
    public UserResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        String rawPassword = (req.password != null && !req.password.isBlank())
                ? req.password
                : "Student@123"; // default password if admin adds student without one

        User user = new User(req.name, req.email, passwordEncoder.encode(rawPassword), "USER");
        return new UserResponse(userRepository.save(user));
    }

    // ── Get all students ──────────────────────────────────────────────────────
    public List<UserResponse> getAllStudents() {
        return userRepository.findAllByOrderByIdAsc()
                .stream()
                .map(UserResponse::new)
                .toList();
    }

    // ── Update ────────────────────────────────────────────────────────────────
    public UserResponse update(Long id, UpdateRequest req) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

        if (req.name  != null && !req.name.isBlank())  user.setName(req.name);
        if (req.email != null && !req.email.isBlank()) {
            if (!req.email.equals(user.getEmail()) && userRepository.existsByEmail(req.email)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
            }
            user.setEmail(req.email);
        }

        return new UserResponse(userRepository.save(user));
    }

    // ── Delete ────────────────────────────────────────────────────────────────
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
        userRepository.deleteById(id);
    }

    // ── Get all users (raw, for Excel export) ─────────────────────────────────
    public List<User> getAllRaw() {
        return userRepository.findAllByOrderByIdAsc();
    }
}
