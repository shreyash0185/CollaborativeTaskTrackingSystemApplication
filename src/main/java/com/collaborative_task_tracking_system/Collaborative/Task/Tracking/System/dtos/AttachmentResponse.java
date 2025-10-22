package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentResponse {
    private String id;
    private String fileName;
    private String fileType;
    private String fileUrl;
    private String uploadedBy;
    private LocalDateTime uploadedAt;


    public AttachmentResponse(String id, String fileName, String fileType, String fileUrlOrPath, String uploadedBy, LocalDateTime uploadedAt) {
    }
}