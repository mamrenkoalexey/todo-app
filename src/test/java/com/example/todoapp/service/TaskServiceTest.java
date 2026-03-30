package com.example.todoapp.service;

import com.example.todoapp.dto.TaskResponse;
import com.example.todoapp.model.Task;
import com.example.todoapp.model.TaskStatus;
import com.example.todoapp.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.Mockito;

import java.util.Optional;

public class TaskServiceTest {

    private final TaskRepository repository = Mockito.mock(TaskRepository.class);
    private final TaskService service = new TaskService(repository);

    @Test
    void getTaskById(){
        Task task = new Task("Test","Test Service", TaskStatus.NEW);

        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.of(task));

        TaskResponse response = service.getTaskById(1L);

       assertEquals("Test", response.getTitle());

    }

}
