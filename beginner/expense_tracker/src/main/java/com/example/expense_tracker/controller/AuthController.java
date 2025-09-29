package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.AuthResponse;
import com.example.expense_tracker.dto.LoginRequest;
import com.example.expense_tracker.dto.SignupRequest;
import com.example.expense_tracker.services.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public AuthResponse signup(@RequestBody SignupRequest request){
        log.info("POST /api/auth/signup - Processing signup for username: {}", request.getUsername());
        return authService.signup(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request){
        log.info("POST /api/auth/login - Processing login for username: {}", request.getUsername());
        return authService.login(request);
    }
}
