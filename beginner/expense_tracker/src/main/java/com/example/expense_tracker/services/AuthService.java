package com.example.expense_tracker.services;

import com.example.expense_tracker.dto.AuthResponse;
import com.example.expense_tracker.dto.LoginRequest;
import com.example.expense_tracker.dto.SignupRequest;
import com.example.expense_tracker.models.User;
import com.example.expense_tracker.repository.UserRepository;
import com.example.expense_tracker.sercurity.JwtUtil;
import com.mongodb.DuplicateKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public AuthResponse signup(SignupRequest request) {
        log.info("Signing up user: {}", request.getUsername());
        // Check if username already exists
        if (userRepository.findByUsername(request.getUsername()) != null) {
            log.error("Username {} already exists", request.getUsername());
            throw new RuntimeException("Username already exists");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        try {
            userRepository.save(user);
            log.info("User {} saved successfully", request.getUsername());
        } catch (DuplicateKeyException e) {
            log.error("Duplicate username detected: {}", request.getUsername());
            throw new RuntimeException("Username already exists");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        log.info("Generated JWT for user: {}", request.getUsername());
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        log.info("Logging in user: {}", request.getUsername());
        User user = userRepository.findByUsername(request.getUsername());
        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = jwtUtil.generateToken(user.getUsername());
            log.info("Login successful for user: {}, generated JWT", request.getUsername());
            return new AuthResponse(token);
        }
        log.error("Invalid credentials for user: {}", request.getUsername());
        throw new RuntimeException("Invalid credentials");
    }
}
