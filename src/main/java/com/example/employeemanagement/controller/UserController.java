package com.example.employeemanagement.controller;

import com.example.employeemanagement.entities.Role;
import com.example.employeemanagement.entities.User;
import com.example.employeemanagement.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Role Management", description = "Endpoints for updating user roles (Admin only)")
public class UserController {

    private final UserRepository userRepository;

    // ---------------------------
    // CHANGE ROLE BY ID
    // ---------------------------

    @Operation(summary = "Make user ADMIN by ID", description = "Admin can promote a user to ROLE_ADMIN using their ID.")
    @PatchMapping("/{id}/make-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> makeAdminById(
            @Parameter(description = "User ID", example = "1")
            @PathVariable Long id
    ) {
        return updateRoleById(id, Role.ROLE_ADMIN);
    }

    @Operation(summary = "Make user USER by ID", description = "Admin can demote a user to ROLE_USER using their ID.")
    @PatchMapping("/{id}/make-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> makeUserById(
            @Parameter(description = "User ID", example = "1")
            @PathVariable Long id
    ) {
        return updateRoleById(id, Role.ROLE_USER);
    }

    private ResponseEntity<User> updateRoleById(Long id, Role newRole) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(newRole);
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    // ---------------------------
    // CHANGE ROLE BY USERNAME
    // ---------------------------

    @Operation(summary = "Make user ADMIN by username", description = "Admin can promote a user to ROLE_ADMIN using their username.")
    @PatchMapping("/username/{username}/make-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> makeAdminByUsername(
            @Parameter(description = "Username", example = "john")
            @PathVariable String username
    ) {
        return updateRoleByUsername(username, Role.ROLE_ADMIN);
    }

    @Operation(summary = "Make user USER by username", description = "Admin can demote a user to ROLE_USER using their username.")
    @PatchMapping("/username/{username}/make-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> makeUserByUsername(
            @Parameter(description = "Username", example = "john")
            @PathVariable String username
    ) {
        return updateRoleByUsername(username, Role.ROLE_USER);
    }

    private ResponseEntity<User> updateRoleByUsername(String username, Role newRole) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(newRole);
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }
}
