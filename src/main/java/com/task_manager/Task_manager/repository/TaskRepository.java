package com.task_manager.Task_manager.repository;

import com.task_manager.Task_manager.entity.Project;
import com.task_manager.Task_manager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProject(Project project);

    long countByProject(Project project);

    long countByProjectAndCompletedTrue(Project project);
}
