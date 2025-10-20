package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String id;
    private String username;
    private String email;
    private String role;
    private String phoneNumber;
    private String address;
    private String profilePictureUrl;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
