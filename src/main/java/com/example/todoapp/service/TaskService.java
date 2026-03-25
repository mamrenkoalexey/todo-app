package com.example.todoapp.service;

import com.example.todoapp.dto.TaskRequest;
import com.example.todoapp.dto.TaskResponse;
import com.example.todoapp.exception.TaskNotFoundException;
import com.example.todoapp.model.Task;
import com.example.todoapp.model.TaskStatus;
import com.example.todoapp.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponse createTask(TaskRequest request) {
        TaskStatus status = request.getStatus() != null ? request.getStatus() : TaskStatus.NEW;

        Task task = taskRepository.save(new Task(request.getTitle(), request.getDescription(), status));
        return TaskResponse.fromEntity(task);
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(TaskResponse::fromEntity)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }

        return TaskResponse.fromEntity(task);
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.delete(task);
    }
}
