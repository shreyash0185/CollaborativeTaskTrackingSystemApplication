package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentRequest {

    @NotBlank(message = "Task ID is required")
    private String taskId;

    @NotBlank(message = "Comment content cannot be empty")
    private String content;
}
