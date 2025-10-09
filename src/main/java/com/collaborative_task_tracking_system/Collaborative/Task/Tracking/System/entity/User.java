package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class User {

    @Id
    private String id; // MongoDB ObjectId
    private String name;
    private String email;
    private String hashPassword;
    private String role; // e.g., ADMIN, MEMBER
    private String phoneNumber;
    private String address;
    private String profilePictureUrl;
    private String status; // ACTIVE / INACTIVE
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Reference IDs for teams this user belongs to
    private List<String> teamIds;
}