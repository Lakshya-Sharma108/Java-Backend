package com.example.studentmanager.config;

import com.example.studentmanager.model.User;
import com.example.studentmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Runs once on startup.
 * Creates a default ADMIN account if no admin exists yet.
 * Credentials are controlled via env vars: ADMIN_EMAIL, ADMIN_PASSWORD
 */
@Component
public class AdminSeeder implements CommandLineRunner {

    @Autowired private UserRepository   userRepository;
    @Autowired private PasswordEncoder  passwordEncoder;

    @Value("${admin.seed.email}")    private String adminEmail;
    @Value("${admin.seed.password}") private String adminPassword;

    @Override
    public void run(String... args) {
        if (userRepository.existsByEmail(adminEmail)) return;

        User admin = new User(
            "Admin",
            adminEmail,
            passwordEncoder.encode(adminPassword),
            "ADMIN"
        );
        userRepository.save(admin);
        System.out.println("✅ Default admin created → " + adminEmail);
    }
}
