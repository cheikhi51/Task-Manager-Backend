package com.taskManager.TaskManager.repository;

import com.taskManager.TaskManager.entity.Project;
import com.taskManager.TaskManager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProject(Project project);

    long countByProject(Project project);

    long countByProjectAndCompletedTrue(Project project);
}
