package com.example.studentmanager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// ── Inbound DTOs ──────────────────────────────────────────────────────────────

/** POST /api/register */
public class Dtos {

    public static class RegisterRequest {
        @NotBlank(message = "Name is required")
        public String name;

        @Email(message = "Invalid email")
        @NotBlank(message = "Email is required")
        public String email;

        // Password optional on registration (admin can add students without one)
        public String password;
    }

    /** POST /login */
    public static class LoginRequest {
        @NotBlank public String email;
        @NotBlank public String password;
    }

    /** PUT /api/students/{id} */
    public static class UpdateRequest {
        public String name;
        public String email;
    }

    // ── Outbound DTOs ─────────────────────────────────────────────────────────

    /** Returned instead of the User entity — never exposes password */
    public static class UserResponse {
        public Long   id;
        public String name;
        public String email;
        public String role;

        public UserResponse(com.example.studentmanager.model.User u) {
            this.id    = u.getId();
            this.name  = u.getName();
            this.email = u.getEmail();
            this.role  = u.getRole();
        }
    }

    /** Returned on login */
    public static class LoginResponse {
        public String token;
        public String role;
        public String email;

        public LoginResponse(String token, String role, String email) {
            this.token = token;
            this.role  = role;
            this.email = email;
        }
    }

    /** Generic API response wrapper */
    public static class ApiResponse {
        public boolean success;
        public String  message;

        public ApiResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }
}
