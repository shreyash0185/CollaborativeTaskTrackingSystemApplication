package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.controller;


import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos.ProjectRequest;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos.ProjectResponse;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectRequest request, String name) {
        return ResponseEntity.ok(projectService.createProject(request, name));
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<ProjectResponse>> getProjectsByTeam(@PathVariable String teamId) {
        return ResponseEntity.ok(projectService.getProjectsByTeam(teamId));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable String projectId) {
        return ResponseEntity.ok(projectService.getProjectById(projectId));
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable String projectId, @RequestBody ProjectRequest request) {
        return ResponseEntity.ok(projectService.updateProject(projectId, request));
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable String projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.ok("Project deleted successfully");
    }
}
