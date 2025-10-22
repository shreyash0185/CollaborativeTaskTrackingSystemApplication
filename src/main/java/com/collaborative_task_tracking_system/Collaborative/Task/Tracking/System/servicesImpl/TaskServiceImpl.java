package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.servicesImpl;

import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.Exception.ResourceNotFoundException;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos.TaskRequest;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.dtos.TaskResponse;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entityM.Project;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entityM.Task;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.entityM.User;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.repository.ProjectRepository;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.repository.TaskRepository;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.repository.TeamRepository;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.repository.UserRepository;
import com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.services.TaskService;
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
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Override
    // Create new task
    @CacheEvict(value = "tasks", allEntries = true)
    public TaskResponse createTask(TaskRequest taskRequest , String createdByEmail) {

        User creator = userRepository.findByEmail(createdByEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // 1️⃣ Validate Project
        Project project = projectRepository.findById(taskRequest.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        // 2️⃣ Validate Team Membership
        boolean isTeamMember = teamRepository.findById(project.getTeamId())
                .map(team -> team.getUserIds().contains(creator.getId()))
                .orElse(false);

        if (!isTeamMember) {
            throw new IllegalStateException("You are not part of this project's team");
        }



        log.info("Creating new task with title: {}", createdByEmail);
        Task task = new Task();
        task.setTaskName(taskRequest.getTaskTitle());
        task.setDescription(taskRequest.getDescription());
        task.setPriority(taskRequest.getPriority());
        task.setStatus("Open");
        task.setDueDate(taskRequest.getDueDate());
        task.setCreatedAt(LocalDateTime.now());
        task.setCreatedBy(createdByEmail);

        Task savedTask = taskRepository.save(task);
        log.info("Task created with ID: {}", savedTask.getId());

        return mapTpoTaskResponse(savedTask);

    }

    @Override
    public List<TaskResponse> getTasksByProject(String projectId) {
        List<Task> tasks = taskRepository.findByProjectId(projectId);
        return tasks.stream().map(this::mapTpoTaskResponse).collect(Collectors.toList());
    }

    @Override
    public List<TaskResponse> getTasksByTeam(String teamId) {
        List<Project> projects = projectRepository.findByTeamId(teamId);
        if (projects.isEmpty()) {
            throw new ResourceNotFoundException("No projects found for team ID: " + teamId);
        }

        List<String> projectIds = projects.stream()
                .map(Project::getId)
                .collect(Collectors.toList());

        List<Task> tasks = taskRepository.findByProjectIdIn(projectIds);
        return tasks.stream().map(this::mapTpoTaskResponse).collect(Collectors.toList());
    }






    // Fetch all tasks for the logged-in user
    @Cacheable(value = "tasks", key = "#email")
    public List<TaskResponse> getTasksForUser(String email) {
        log.info("Fetching tasks for user: {}", email);
        return taskRepository.findAll().stream()
                .filter(t -> email.equalsIgnoreCase(t.getCreatedBy()) || email.equalsIgnoreCase(String.valueOf(t.getAssignedToUserId())))
                .map(this::mapTpoTaskResponse)
                .collect(Collectors.toList());
    }

    // Update task
    @CacheEvict(value = "tasks", allEntries = true)
    public TaskResponse updateTask(String id, TaskRequest request) {
        log.info("Updating task with ID: {}", id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));

        if (request.getTaskTitle() != null) task.setTaskName(request.getTaskTitle());
        if (request.getDescription() != null) task.setDescription(request.getDescription());
        if (request.getPriority() != null) task.setPriority(request.getPriority());
        if (request.getStatus() != null) task.setStatus(request.getStatus());
        if (request.getDueDate() != null) task.setDueDate(request.getDueDate());

        Task updated = taskRepository.save(task);
        return mapTpoTaskResponse(updated);
    }





    // Delete task
    @CacheEvict(value = "tasks", allEntries = true)
    public void deleteTask(String id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with ID: " + id);
        }
        taskRepository.deleteById(id);
        log.info("Deleted task with ID: {}", id);
    }

    // Search tasks
    @Cacheable(value = "tasks-search", key = "#keyword")
    public List<TaskResponse> searchTasks(String keyword) {
        log.info("Searching tasks by keyword: {}", keyword);
        return taskRepository.findByTaskNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword)
                .stream().map(this::mapTpoTaskResponse).collect(Collectors.toList());
    }

    private TaskResponse mapTpoTaskResponse(Task task) {
        return new TaskResponse(
                String.valueOf(task.getId()),
                task.getTaskName(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                String.valueOf(task.getAssignedToUserId()),
                String.valueOf(task.getProjectId()),
                task.getCreatedAt()
        );
    }

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }

    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
}
