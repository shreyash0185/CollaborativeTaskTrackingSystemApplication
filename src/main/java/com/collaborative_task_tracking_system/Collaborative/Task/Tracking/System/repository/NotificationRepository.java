package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.repository;

import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entityM.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByUserId(String userId);
}
