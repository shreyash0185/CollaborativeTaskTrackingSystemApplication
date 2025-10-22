package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.services;

import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos.ProjectRequest;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos.ProjectResponse;

import java.util.List;

public interface ProjectService {
    public ProjectResponse createProject(ProjectRequest request, String createdByEmail);
    public List<ProjectResponse> getProjectsByTeam(String teamId);
    public ProjectResponse getProjectById(String projectId);
    public ProjectResponse updateProject(String projectId, ProjectRequest request);
    public void deleteProject(String projectId);

}
