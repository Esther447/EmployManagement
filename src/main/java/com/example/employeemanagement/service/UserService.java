package com.example.employeemanagement.service;

import com.example.employeemanagement.DTO.AuthRequest;
import com.example.employeemanagement.DTO.AuthResponse;
import com.example.employeemanagement.entities.User;
import com.example.employeemanagement.repository.UserRepository;
import com.example.employeemanagement.service.JwtService;
import com.example.employeemanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.employeemanagement.entities.Role;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public User register(User user) {
        if (userRepository.existsByUsername(user.getUsername()))
            throw new RuntimeException("Username is already taken");

        if (userRepository.existsByEmail(user.getEmail()))
            throw new RuntimeException("Email is already in use");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) user.setRole(com.example.employeemanagement.entities.Role.ROLE_USER);
        return userRepository.save(user);
    }

    public AuthResponse authenticate(AuthRequest authRequest) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        // Set authentication in context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Get UserDetails from authentication
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        // Load the user from the database
        User user = userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate JWT token using the new method
        String jwt = jwtService.generateToken(principal, user.getRole().name());

        return new AuthResponse(jwt, "Bearer", user.getUsername(), user.getRole().name());
    }

}
