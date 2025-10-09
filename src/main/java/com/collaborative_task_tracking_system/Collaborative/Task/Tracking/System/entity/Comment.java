package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Comment {

    private String userId; // user who wrote the comment
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
