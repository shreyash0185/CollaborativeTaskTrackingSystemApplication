package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entity;


import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Attachment {

    private String fileName;
    private String fileType;
    private String fileUrlOrPath;
    private LocalDateTime uploadedAt;
    private String uploadedBy; // userId of uploader
}

