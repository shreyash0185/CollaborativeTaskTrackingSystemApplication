package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {

    private String id;
    private String taskName;
    private String description;
    private String status;
    private String priority;
    private LocalDate dueDate;
    private String assignedTo;
    private String projectId;
    private LocalDateTime createdAt;
}
