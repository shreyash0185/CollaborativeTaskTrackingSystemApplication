package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Document(collection = "attachments")
public class Attachment {

    @Id
    private String id;
    private String fileName;
    private String fileType;
    private String fileUrlOrPath;
    private LocalDateTime uploadedAt;
    private String uploadedBy; // userId of uploader
}

