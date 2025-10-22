package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String role; // e.g., ADMIN / MEMBER / USER
}