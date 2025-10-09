package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "teams")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Team {

    @Id
    private String id;
    private String teamName;
    private String description;
    private String status; // ACTIVE / INACTIVE
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // References: list of user IDs in this team
    private List<String> userIds;

    // References: list of project IDs under this team
    private List<String> projectIds;
}