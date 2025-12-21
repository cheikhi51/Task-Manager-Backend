package com.task_manager.Task_manager.service;

import com.task_manager.Task_manager.dto.project.ProjectProgressResponse;
import com.task_manager.Task_manager.dto.project.ProjectRequest;
import com.task_manager.Task_manager.dto.project.ProjectResponse;
import com.task_manager.Task_manager.entity.Project;
import com.task_manager.Task_manager.entity.User;
import com.task_manager.Task_manager.exception.ResourceNotFoundException;
import com.task_manager.Task_manager.repository.ProjectRepository;
import com.task_manager.Task_manager.repository.TaskRepository;
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
