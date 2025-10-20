package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.services;

import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos.TaskRequest;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos.TaskResponse;

import java.util.List;

public interface TaskService {


    public TaskResponse createTask(TaskRequest taskRequest , String createdByEmail);
    public TaskResponse updateTask(String taskId, TaskRequest taskRequest );
    public void deleteTask(String taskId );
    public List<TaskResponse> searchTasks(String keyword);
    public List<TaskResponse> getTasksForUser(String userEmail);

}
