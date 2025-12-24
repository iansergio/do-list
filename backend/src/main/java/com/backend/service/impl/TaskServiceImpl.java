package com.backend.service.impl;

import com.backend.dto.TaskRequest;
import com.backend.domain.task.Task;
import com.backend.domain.task.TaskPriority;
import com.backend.domain.task.TaskStatus;
import com.backend.repository.TaskRepository;
import com.backend.service.TaskService;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Task save(TaskRequest request) {
        Task task = new Task(
                request.title(),
                request.description(),
                request.priority(),
                request.status(),
                request.dueDate(),
                request.userId()
        );

        if (request.priority() == null) {
            task.setPriority(TaskPriority.HIGH);
        }

        if (request.status() == null) {
            task.setStatus(TaskStatus.PENDING);
        }

        return repository.save(task);
    }
}
