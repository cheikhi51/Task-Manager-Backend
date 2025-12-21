package com.task_manager.Task_manager.service;

import com.task_manager.Task_manager.dto.task.TaskRequest;
import com.task_manager.Task_manager.dto.task.TaskResponse;
import com.task_manager.Task_manager.entity.Project;
import com.task_manager.Task_manager.entity.Task;
import com.task_manager.Task_manager.entity.User;
import com.task_manager.Task_manager.exception.ResourceNotFoundException;
import com.task_manager.Task_manager.repository.ProjectRepository;
import com.task_manager.Task_manager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository,
                       ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public TaskResponse createTask(Long projectId, TaskRequest request, User user) {

        Project project = projectRepository
                .findByIdAndUser(projectId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setProject(project);

        Task savedTask = taskRepository.save(task);

        return mapToResponse(savedTask);
    }

    public List<TaskResponse> getProjectTasks(Long projectId, User user) {

        Project project = projectRepository
                .findByIdAndUser(projectId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        return taskRepository.findByProject(project)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public void markTaskCompleted(Long taskId, User user) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (!task.getProject().getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Task not found");
        }

        task.setCompleted(true);
        taskRepository.save(task);
    }

    public void deleteTask(Long taskId, User user) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (!task.getProject().getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Task not found");
        }

        taskRepository.delete(task);
    }

    private TaskResponse mapToResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.isCompleted()
        );
    }
}
