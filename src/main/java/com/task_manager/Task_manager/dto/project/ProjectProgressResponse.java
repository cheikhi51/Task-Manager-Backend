package com.task_manager.Task_manager.dto.project;

public class ProjectProgressResponse {

    private long totalTasks;
    private long completedTasks;
    private int progressPercentage;

    public ProjectProgressResponse(long totalTasks, long completedTasks, int progressPercentage) {
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.progressPercentage = progressPercentage;
    }

    public long getTotalTasks() {
        return totalTasks;
    }

    public long getCompletedTasks() {
        return completedTasks;
    }

    public int getProgressPercentage() {
        return progressPercentage;
    }
}
