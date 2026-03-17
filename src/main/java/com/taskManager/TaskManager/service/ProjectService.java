package com.taskManager.TaskManager.service;

import com.taskManager.TaskManager.dto.project.ProjectProgressResponse;
import com.taskManager.TaskManager.dto.project.ProjectRequest;
import com.taskManager.TaskManager.dto.project.ProjectResponse;
import com.taskManager.TaskManager.entity.Project;
import com.taskManager.TaskManager.entity.User;
import com.taskManager.TaskManager.exception.ResourceNotFoundException;
import com.taskManager.TaskManager.repository.ProjectRepository;
import com.taskManager.TaskManager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public ProjectService(ProjectRepository projectRepository,
                          TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    public ProjectResponse createProject(ProjectRequest request, User user) {

        Project project = new Project();
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setUser(user);

        Project savedProject = projectRepository.save(project);

        return new ProjectResponse(
                savedProject.getId(),
                savedProject.getTitle(),
                savedProject.getDescription()
        );
    }

    public List<ProjectResponse> getUserProjects(User user) {
        return projectRepository.findByUser(user)
                .stream()
                .map(p -> new ProjectResponse(
                        p.getId(),
                        p.getTitle(),
                        p.getDescription()
                ))
                .toList();
    }

    public ProjectResponse getProjectById(Long projectId, User user) {

        Project project = projectRepository
                .findByIdAndUser(projectId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        return new ProjectResponse(
                project.getId(),
                project.getTitle(),
                project.getDescription()
        );
    }

    public ProjectProgressResponse getProjectProgress(Long projectId, User user) {

        Project project = projectRepository
                .findByIdAndUser(projectId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        long totalTasks = taskRepository.countByProject(project);
        long completedTasks = taskRepository.countByProjectAndCompletedTrue(project);

        int progress = totalTasks == 0
                ? 0
                : (int) ((completedTasks * 100) / totalTasks);

        return new ProjectProgressResponse(totalTasks, completedTasks, progress);
    }
}
