package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.servicesImpl;

import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.Exception.ResourceNotFoundException;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos.TeamRequest;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos.TeamResponse;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entityM.Team;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entityM.User;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.repository.TeamRepository;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.repository.UserRepository;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.services.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    // Create new team
    @CacheEvict(value = "teams", allEntries = true)
    public TeamResponse createTeam(TeamRequest request, String createdByEmail) {
        log.info("Creating new team by {}", createdByEmail);

        User creator = userRepository.findByEmail(createdByEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Team team = new Team();
        team.setTeamName(request.getTeamName());
        team.setDescription(request.getDescription());
        team.setStatus("ACTIVE");
        team.setCreatedAt(LocalDateTime.now());
        team.setUpdatedAt(LocalDateTime.now());
        team.setUserIds(request.getUsers().stream()
                .map(user -> String.valueOf(user.getId()))
                .collect(Collectors.toList()));

        Team saved = teamRepository.save(team);
        return mapToResponse(saved);
    }

    // Get teams of logged-in user
    @Cacheable(value = "teams", key = "#email")
    public List<TeamResponse> getTeamsForUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return teamRepository.findByUsersUserId(String.valueOf(user.getId()))
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    // Join team
    @CacheEvict(value = "teams", allEntries = true)
    public TeamResponse joinTeam(String teamId, String email) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        team.getUserIds().add(String.valueOf(user.getId()));

        return mapToResponse(teamRepository.save(team));
    }

    // Leave team
    @CacheEvict(value = "teams", allEntries = true)
    public void leaveTeam(String teamId, String email) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        team.getUserIds().remove(String.valueOf(user.getId()));
        teamRepository.save(team);
        log.info("{} left team {}", email, team.getTeamName());
    }

    private TeamResponse mapToResponse(Team team) {
        List<String> members = team.getUserIds().stream()
                .map(userId -> userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId))
                        .getEmail())
                .collect(Collectors.toList());


        return new TeamResponse(
                String.valueOf(team.getId()),
                team.getTeamName(),
                team.getDescription(),
                team.getStatus(),
                team.getCreatedAt(),
                team.getUpdatedAt(),
                members
        );
    }
}