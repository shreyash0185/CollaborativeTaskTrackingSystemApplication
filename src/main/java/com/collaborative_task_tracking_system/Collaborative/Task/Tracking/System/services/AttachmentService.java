package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.services;

import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos.AttachmentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AttachmentService {
    AttachmentResponse uploadAttachment(String taskId, MultipartFile file, String uploadedBy) throws IOException, IOException;
    List<AttachmentResponse> getAttachmentsByTask(String taskId);
    void deleteAttachment(String taskId, String attachmentId, String userEmail);
}