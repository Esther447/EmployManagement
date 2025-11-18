package com.example.employeemanagement.controller;

import com.example.employeemanagement.DTO.AuthRequest;
import com.example.employeemanagement.DTO.AuthResponse;
import com.example.employeemanagement.entities.User;
import com.example.employeemanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        User registeredUser = userService.register(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = userService.authenticate(authRequest);
        return ResponseEntity.ok(authResponse);
    }
}