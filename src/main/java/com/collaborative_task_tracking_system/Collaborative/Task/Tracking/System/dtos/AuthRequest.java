package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos;


import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}