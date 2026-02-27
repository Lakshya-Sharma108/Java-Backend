package com.example.studentmanager.controller;

import com.example.studentmanager.dto.Dtos.*;
import com.example.studentmanager.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * POST /api/register  (Public)
     * Registers a new student/user.
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.register(req));
    }

    /**
     * GET /api/students  (Admin only)
     * Returns all users sorted by ID.
     */
    @GetMapping("/students")
    public ResponseEntity<List<UserResponse>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    /**
     * PUT /api/students/{id}  (Admin only)
     * Updates name and/or email of a student.
     */
    @PutMapping("/students/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id,
                                               @RequestBody UpdateRequest req) {
        return ResponseEntity.ok(studentService.update(id, req));
    }

    /**
     * DELETE /api/students/{id}  (Admin only)
     */
    @DeleteMapping("/students/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.ok(new ApiResponse(true, "Student deleted successfully"));
    }
}
