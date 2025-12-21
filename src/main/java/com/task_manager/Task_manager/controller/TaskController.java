package com.task_manager.Task_manager.controller;

import com.task_manager.Task_manager.dto.task.TaskRequest;
import com.task_manager.Task_manager.dto.task.TaskResponse;
import com.task_manager.Task_manager.entity.User;
import com.task_manager.Task_manager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    @PostMapping("/projects/{projectId}/tasks")
    public ResponseEntity<TaskResponse> createTask(
            @PathVariable Long projectId,
            @Valid @RequestBody TaskRequest request) {

        User user = getCurrentUser();
        return ResponseEntity.ok(taskService.createTask(projectId, request, user));
    }

    @GetMapping("/projects/{projectId}/tasks")
    public ResponseEntity<List<TaskResponse>> getProjectTasks(
            @PathVariable Long projectId) {

        User user = getCurrentUser();
        return ResponseEntity.ok(taskService.getProjectTasks(projectId, user));
    }

    @PutMapping("/tasks/{taskId}/complete")
    public ResponseEntity<Void> completeTask(@PathVariable Long taskId) {

        User user = getCurrentUser();
        taskService.markTaskCompleted(taskId, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {

        User user = getCurrentUser();
        taskService.deleteTask(taskId, user);
        return ResponseEntity.noContent().build();
    }
}
