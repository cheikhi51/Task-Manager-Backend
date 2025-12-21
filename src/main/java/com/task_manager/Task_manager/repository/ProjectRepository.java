package com.task_manager.Task_manager.repository;

import com.task_manager.Task_manager.entity.Project;
import com.task_manager.Task_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByUser(User user);

    Optional<Project> findByIdAndUser(Long id, User user);
}
