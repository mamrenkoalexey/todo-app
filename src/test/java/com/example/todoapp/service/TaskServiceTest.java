package com.example.todoapp.service;

import com.example.todoapp.dto.TaskRequest;
import com.example.todoapp.dto.TaskResponse;
import com.example.todoapp.exception.TaskNotFoundException;
import com.example.todoapp.model.Task;
import com.example.todoapp.model.TaskStatus;
import com.example.todoapp.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

public class TaskServiceTest {

    private final TaskRepository repository = Mockito.mock(TaskRepository.class);
    private final TaskService service = new TaskService(repository);

    @Test
    void getTaskById() {
        Task task = new Task("Task", "desc", TaskStatus.NEW);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(task));

        TaskResponse response = service.getTaskById(1L);
        assertEquals("Task", response.getTitle());
    }

    @Test
    void getTaskByIdException() {
        Mockito.when(repository.findById(88L)).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> service.getTaskById(88L));
    }

    @Test
    void getAllTasks() {
        Task task1 = new Task("Task 1", "desc", TaskStatus.NEW);
        Task task2 = new Task("Task 2", "desc", TaskStatus.IN_PROGRESS);

        Mockito.when(repository.findAll()).thenReturn(List.of(task1, task2));
        List<TaskResponse> result = service.getAllTasks();
        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
        assertEquals("Task 2", result.get(1).getTitle());
    }

    @Test
    void getAllTasksException() {
        Mockito.when(repository.findAll()).thenReturn(List.of());

        List<TaskResponse> result = service.getAllTasks();
        assertTrue(result.isEmpty());
    }

    @Test
    void updateById() {
        Task existing = new Task("Task 1", "desc", TaskStatus.NEW);
        TaskRequest request = new TaskRequest("New Task 1", "New desc", TaskStatus.DONE);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(existing));
        TaskResponse response = service.updateTask(1L, request);

        assertEquals("New Task 1", response.getTitle());
        assertEquals("New desc", response.getDescription());
        assertEquals(TaskStatus.DONE, response.getStatus());
    }

    @Test
    void updateByIdException() {
        Task existing = new Task("Task 1", "desc", TaskStatus.NEW);
        TaskRequest request = new TaskRequest("New Task 1", null, null);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(existing));
        TaskResponse response = service.updateTask(1L, request);


        assertEquals(null, response.getDescription());
        assertEquals(TaskStatus.NEW, response.getStatus());
    }

    @Test
    void deleteById() {
        Task task = new Task("Task", "desc", TaskStatus.NEW);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(task));

        service.deleteTask(1L);
        verify(repository).delete(task);
    }

    @Test
    void deleteByIdException() {
        Mockito.when(repository.findById(88L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> service.deleteTask(88L));
    }
}
