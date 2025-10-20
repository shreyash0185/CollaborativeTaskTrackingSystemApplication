package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.repository;

import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entityM.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TeamRepository extends MongoRepository<Team, String> {
    List<Team> findByUsersUserId(String userId);
}
