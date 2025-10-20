package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequest {

    @NotBlank(message = "Title is mandatory")
    private String taskTitle;

    private String description;

    @FutureOrPresent(message = "Due date must be in the present or future")
    private LocalDate dueDate;
    private String status;

    private String priority;
    private String string;
    private String projectId;




}
