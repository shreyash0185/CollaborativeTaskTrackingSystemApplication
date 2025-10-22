package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.controller;

import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos.UserDTO;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.servicesImpl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    // Fetch currently logged-in user's info
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        UserDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    // Update current user profile
    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(
            Authentication authentication,
            @RequestBody UserDTO updatedData
    ) {
        String email = authentication.getName();
        UserDTO updatedUser = userService.updateUser(email, updatedData);
        return ResponseEntity.ok(updatedUser);
    }
}