package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.servicesImpl;

import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.repository.TaskRepository;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.repository.UserRepository;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.services.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired private UserRepository userRepository;

    @Override
    public CommentResponse addComment(CommentRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Task task = taskRepository.findById(request.getTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        Comment comment = new Comment();
        comment.setId(UUID.randomUUID().toString());
        comment.setContent(request.getContent());
        comment.setUserId(user.getId());
        comment.setCreatedAt(LocalDateTime.now());

        if (task.getComments() == null)
            task.setComments(new java.util.ArrayList<>());

        task.getComments().add(comment);
        taskRepository.save(task);

        log.info("User {} added comment to Task {}", userEmail, task.getId());

        return new CommentResponse(comment.getId(), comment.getContent(), userEmail, comment.getCreatedAt());
    }

    @Override
    public List<CommentResponse> getCommentsByTask(String taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        return task.getComments().stream()
                .map(c -> new CommentResponse(c.getId(), c.getContent(), c.getUserId(), c.getCreatedAt()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(String taskId, String commentId, String userEmail) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        boolean removed = task.getComments().removeIf(
                c -> c.getId().equals(commentId) &&
                        (c.getUserId().equalsIgnoreCase(userEmail) || userEmail.equalsIgnoreCase("admin"))
        );

        if (!removed) {
            throw new IllegalStateException("Comment not found or you don’t have permission to delete it");
        }

        taskRepository.save(task);
        log.info("Comment {} removed from Task {} by {}", commentId, taskId, userEmail);
    }
}