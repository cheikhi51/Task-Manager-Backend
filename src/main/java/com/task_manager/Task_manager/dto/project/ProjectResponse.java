package com.task_manager.Task_manager.dto.project;

public class ProjectResponse {

    private Long id;
    private String title;
    private String description;

    public ProjectResponse(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
