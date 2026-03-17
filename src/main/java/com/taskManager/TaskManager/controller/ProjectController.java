package com.taskManager.TaskManager.controller;

import com.taskManager.TaskManager.dto.project.ProjectProgressResponse;
import com.taskManager.TaskManager.dto.project.ProjectRequest;
import com.taskManager.TaskManager.dto.project.ProjectResponse;
import com.taskManager.TaskManager.entity.User;
import com.taskManager.TaskManager.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "http://localhost:5173/")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @Valid @RequestBody ProjectRequest request) {

        User user = getCurrentUser();
        return ResponseEntity.ok(projectService.createProject(request, user));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getUserProjects() {

        User user = getCurrentUser();
        return ResponseEntity.ok(projectService.getUserProjects(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id) {

        User user = getCurrentUser();
        return ResponseEntity.ok(projectService.getProjectById(id, user));
    }

    @GetMapping("/{id}/progress")
    public ResponseEntity<ProjectProgressResponse> getProjectProgress(
            @PathVariable Long id) {

        User user = getCurrentUser();
        return ResponseEntity.ok(projectService.getProjectProgress(id, user));
    }
}
