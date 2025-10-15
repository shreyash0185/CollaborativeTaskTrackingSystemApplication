package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.services;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService  extends UserDetailsService {
    String authenticate(String username, String password);
}
