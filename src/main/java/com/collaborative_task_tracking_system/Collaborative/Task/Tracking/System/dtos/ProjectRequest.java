package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectRequest {
    @NotBlank(message = "Project name is required")
    private String projectName;

    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String teamId;
}