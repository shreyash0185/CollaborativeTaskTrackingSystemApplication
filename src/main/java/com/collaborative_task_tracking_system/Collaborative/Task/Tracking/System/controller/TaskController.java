package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.controller;

import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos.TaskRequest;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos.TaskResponse;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    //Create new task
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest taskRequest ,  String createdByEmail) {
        // Implementation goes here
        TaskResponse response = taskService.createTask(taskRequest , createdByEmail);
        return ResponseEntity.ok(response);
    }

    // Fetch all tasks for logged-in user
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks(String id) {
        return ResponseEntity.ok(taskService.getTasksForUser(id));
    }

    // Update task by ID
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable String id, @RequestBody TaskRequest request) {
        return ResponseEntity.ok(taskService.updateTask(id, request));
    }

    // Delete task
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deleted successfully");
    }

    // Search task by keyword
    @GetMapping("/search")
    public ResponseEntity<List<TaskResponse>> searchTasks(@RequestParam String keyword) {
        return ResponseEntity.ok(taskService.searchTasks(keyword));
    }

}
