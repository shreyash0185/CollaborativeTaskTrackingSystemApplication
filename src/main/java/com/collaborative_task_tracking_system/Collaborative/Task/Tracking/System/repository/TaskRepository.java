package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.repository;

import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entityM.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

    List<Task> findByProjectId(String projectId);
    List<Task> findByStatus(String status);
    List<Task> findByAssignedTo(String assignedTo);
    List<Task> findByTaskNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);
}
