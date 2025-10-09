package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "tasks")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Task {

    @Id
    private String id;
    private String taskName;
    private String description;
    private String status;   // OPEN, IN_PROGRESS, COMPLETED
    private String priority; // LOW, MEDIUM, HIGH
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private String createdBy; // userId reference
    private String assignedToUserId; // assigned user reference
    private String projectId; // project reference

    // Embedded comments
    private List<Comment> comments;

    // Embedded attachments
    private List<Attachment> attachments;
}