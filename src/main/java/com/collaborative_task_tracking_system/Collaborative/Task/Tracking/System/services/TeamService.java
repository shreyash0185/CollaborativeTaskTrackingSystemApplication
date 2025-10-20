package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.services;


import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos.TeamRequest;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos.TeamResponse;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entityM.Team;

import java.util.List;

public interface TeamService {

    public TeamResponse createTeam(TeamRequest request, String createdByEmail);
    public List<TeamResponse> getTeamsForUser(String email);
    public TeamResponse joinTeam(String teamId, String email);
    public void leaveTeam(String teamId, String email);


}
