package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.services;

import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entityM.Notification;

import java.util.List;

public interface NotificationService {
    Notification createNotification(String userId, String message);
    List<Notification> getNotifications(String userId);
}
