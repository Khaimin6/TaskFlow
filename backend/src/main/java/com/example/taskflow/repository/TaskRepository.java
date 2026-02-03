package com.example.taskflow.repository;

import com.example.taskflow.model.Task;
import com.example.taskflow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);

    List<Task> findByUserAndStatus(User user, Task.Status status);
}
