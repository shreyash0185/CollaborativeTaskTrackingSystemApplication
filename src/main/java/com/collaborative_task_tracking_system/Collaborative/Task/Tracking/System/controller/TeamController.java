package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.controller;

import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos.TeamRequest;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos.TeamResponse;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.services.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamResponse> createTeam(@Valid @RequestBody TeamRequest request, Authentication auth) {
        return ResponseEntity.ok(teamService.createTeam(request, auth.getName()));
    }

    @GetMapping
    public ResponseEntity<List<TeamResponse>> getTeams(Authentication auth) {
        return ResponseEntity.ok(teamService.getTeamsForUser(auth.getName()));
    }

    @PostMapping("/{teamId}/join")
    public ResponseEntity<TeamResponse> joinTeam(@PathVariable String teamId, Authentication auth) {
        return ResponseEntity.ok(teamService.joinTeam(teamId, auth.getName()));
    }

    @DeleteMapping("/{teamId}/leave")
    public ResponseEntity<String> leaveTeam(@PathVariable String teamId, Authentication auth) {
        teamService.leaveTeam(teamId, auth.getName());
        return ResponseEntity.ok("You have left the team successfully");
    }
}