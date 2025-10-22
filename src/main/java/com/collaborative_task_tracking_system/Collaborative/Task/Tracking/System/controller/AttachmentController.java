package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.controller;

import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos.AttachmentResponse;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.services.AttachmentService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/attachments")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("/upload")
    public ResponseEntity<AttachmentResponse> uploadAttachment(@RequestParam("taskId") String taskId,
                                                               @RequestParam("file") MultipartFile file,
                                                               String name ) throws IOException {
        return ResponseEntity.ok(attachmentService.uploadAttachment(taskId, file, name));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<List<AttachmentResponse>> getAttachmentsByTask(@PathVariable String taskId) {
        return ResponseEntity.ok(attachmentService.getAttachmentsByTask(taskId));
    }

    @DeleteMapping("/{taskId}/{attachmentId}")
    public ResponseEntity<String> deleteAttachment(@PathVariable String taskId,
                                                   @PathVariable String attachmentId,
                                                   String name ) {
        attachmentService.deleteAttachment(taskId, attachmentId, name);
        return ResponseEntity.ok("Attachment deleted successfully");
    }
}