package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.servicesImpl;

import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.Exception.ResourceNotFoundException;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos.ProjectRequest;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos.ProjectResponse;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entityM.Project;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entityM.Team;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entityM.User;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.repository.ProjectRepository;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.repository.TeamRepository;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProjectServiceImpl {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @CacheEvict(value = "projects", allEntries = true)
    public ProjectResponse createProject(ProjectRequest request, String createdByEmail) {
        log.info("Creating new project: {} by {}", request.getProjectName(), createdByEmail);

        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        User creator = userRepository.findByEmail(createdByEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Project project = new Project();
        project.setProjectName(request.getProjectName());
        project.setDescription(request.getDescription());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setStatus("ACTIVE");
        project.setTeamId(request.getTeamId());
        project.setCreatedBy(creator.getEmail());

        Project saved = projectRepository.save(project);
        return mapToResponse(saved);
    }

    @Cacheable(value = "projects", key = "#teamId")
    public List<ProjectResponse> getProjectsByTeam(String teamId) {
        log.info("Fetching projects for team {}", teamId);
        return projectRepository.findByTeamTeamId(teamId)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public ProjectResponse getProjectById(String projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        return mapToResponse(project);
    }

    @CacheEvict(value = "projects", allEntries = true)
    public ProjectResponse updateProject(String projectId, ProjectRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        if (request.getProjectName() != null) project.setProjectName(request.getProjectName());
        if (request.getDescription() != null) project.setDescription(request.getDescription());
        if (request.getStartDate() != null) project.setStartDate(request.getStartDate());
        if (request.getEndDate() != null) project.setEndDate(request.getEndDate());

        Project updated = projectRepository.save(project);
        return mapToResponse(updated);
    }

    @CacheEvict(value = "projects", allEntries = true)
    public void deleteProject(String projectId) {
        if (!projectRepository.existsById(projectId))
            throw new ResourceNotFoundException("Project not found");

        projectRepository.deleteById(projectId);
        log.info("Deleted project with ID {}", projectId);
    }

    private ProjectResponse mapToResponse(Project project) {
        return new ProjectResponse(
                String.valueOf(project.getId()),
                project.getProjectName(),
                project.getDescription(),
                project.getStartDate(),
                project.getEndDate(),
                project.getStatus(),
                project.getTeamId() != null ? String.valueOf(project.getTeamId()) : null,
                project.getCreatedBy()
        );
    }
}