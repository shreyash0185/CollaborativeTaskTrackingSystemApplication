package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos;

import jakarta.validation.constraints.NotBlank;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entityM.User;
import lombok.Data;

import java.util.List;

@Data
public class TeamRequest {
    @NotBlank(message = "Team name is required")
    private String teamName;
    private List<User> users;

    private String description;
}
