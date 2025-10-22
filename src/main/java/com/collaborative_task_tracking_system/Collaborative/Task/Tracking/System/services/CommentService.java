package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.services;

import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos.CommentRequest;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentResponse addComment(CommentRequest request, String userEmail);
    List<CommentResponse> getCommentsByTask(String taskId);
    void deleteComment(String taskId, String commentId, String userEmail);
}
