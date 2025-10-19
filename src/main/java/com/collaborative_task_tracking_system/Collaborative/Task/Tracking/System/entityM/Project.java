package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entityM;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "projects")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Project {

    @Id
    private String id;
    private String projectName;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status; // ACTIVE / COMPLETED / ARCHIVED
    private String createdBy; // userId reference
    private LocalDateTime createdAt;

    // Reference IDs
    private String teamId; // which team owns this project
    private List<String> taskIds; // tasks under this project
}