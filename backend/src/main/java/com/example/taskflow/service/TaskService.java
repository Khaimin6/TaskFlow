package com.example.taskflow.service;

import com.example.taskflow.model.Task;
import com.example.taskflow.model.User;
import com.example.taskflow.repository.TaskRepository;
import com.example.taskflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Task> getTasksByUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return taskRepository.findByUser(user);
    }

    public Task createTask(Task task, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        task.setUser(user);
        if (task.getStatus() == null) {
            task.setStatus(Task.Status.TODO);
        }
        // Default priority if null?
        if (task.getPriority() == null) {
            task.setPriority(Task.Priority.MEDIUM);
        }
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());
        task.setPriority(taskDetails.getPriority());
        task.setDueDate(taskDetails.getDueDate());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
