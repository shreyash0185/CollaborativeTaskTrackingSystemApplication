package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.servicesImpl;

import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.Exception.ResourceNotFoundException;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos.AttachmentResponse;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entityM.Attachment;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entityM.Task;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.repository.TaskRepository;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.services.AttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private TaskRepository taskRepository;

    private static final String UPLOAD_DIR = "uploads/";

    @Override
    public AttachmentResponse uploadAttachment(String taskId, MultipartFile file, String uploadedBy) throws IOException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        // Ensure upload directory exists
        Files.createDirectories(Paths.get(UPLOAD_DIR));

        String fileId = UUID.randomUUID().toString();
        String fileName = fileId + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        file.transferTo(filePath);

        Attachment attachment = new Attachment();
        attachment.setId(fileId);
        attachment.setFileName(file.getOriginalFilename());
        attachment.setFileType(file.getContentType());
        attachment.setFileUrlOrPath(filePath.toString());
        attachment.setUploadedAt(LocalDateTime.now());
        attachment.setUploadedBy(uploadedBy);

        if (task.getAttachments() == null)
            task.setAttachments(new java.util.ArrayList<>());

        task.getAttachments().add(attachment);
        taskRepository.save(task);

        log.info("Attachment {} uploaded by {} for Task {}", fileName, uploadedBy, taskId);

        return new AttachmentResponse(
                attachment.getId(),
                attachment.getFileName(),
                attachment.getFileType(),
                attachment.getFileUrlOrPath(),
                uploadedBy,
                attachment.getUploadedAt()
        );
    }

    @Override
    public List<AttachmentResponse> getAttachmentsByTask(String taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        return task.getAttachments().stream()
                .map(a -> new AttachmentResponse(
                        a.getId(),
                        a.getFileName(),
                        a.getFileType(),
                        a.getFileUrlOrPath(),
                        a.getUploadedBy(),
                        a.getUploadedAt()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAttachment(String taskId, String attachmentId, String userEmail) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        boolean removed = task.getAttachments().removeIf(a ->
                a.getId().equals(attachmentId) &&
                        (a.getUploadedBy().equalsIgnoreCase(userEmail) || userEmail.equalsIgnoreCase("admin"))
        );

        if (!removed)
            throw new IllegalStateException("Attachment not found or no permission to delete");

        taskRepository.save(task);
        log.info("Attachment {} deleted from Task {} by {}", attachmentId, taskId, userEmail);
    }
}