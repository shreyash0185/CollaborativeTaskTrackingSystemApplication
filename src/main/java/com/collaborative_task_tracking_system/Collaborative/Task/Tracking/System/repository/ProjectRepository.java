package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.repository;

import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entityM.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
    List<Project> findByTeamTeamId(String teamId);
}
