package com.backend.service.impl;

import com.backend.model.entity.Task;
import com.backend.model.entity.User;
import com.backend.dto.request.CreateTaskRequest;
import com.backend.dto.response.TaskResponse;
import com.backend.dto.request.UpdateTaskRequest;
import com.backend.dto.request.UpdateTaskStatusRequest;
import com.backend.exception.TaskNotFoundException;
import com.backend.repository.TaskRepository;
import com.backend.repository.UserRepository;
import com.backend.service.TaskService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TaskResponse save(CreateTaskRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Task task = request.toEntity(user);

        Task savedTask = taskRepository.save(task);

        return new TaskResponse(
                savedTask.getId(),
                savedTask.getTitle(),
                savedTask.getDescription(),
                savedTask.getPriority(),
                savedTask.getStatus(),
                savedTask.getDueDate(),
                savedTask.getUser().getId()
        );
    }

    @Override
    public List<TaskResponse> findAll() {
        return taskRepository.findAll()
                .stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }

    @Override
    public List<TaskResponse> findAllByUser(String userEmail) {
        return taskRepository.findAllByUserEmail(userEmail)
                .stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }

    @Override
    public Optional<TaskResponse> findById(UUID id) {
        return taskRepository.findById(id)
                .map(TaskResponse::fromEntity);
    }

    @Override
    public Optional<TaskResponse> findByIdAndUser(UUID id, String userEmail) {
        return taskRepository.findById(id)
                .filter(task -> task.getUser().getEmail().equals(userEmail))
                .map(TaskResponse::fromEntity);
    }

    @Override
    public void delete(UUID id, String userEmail) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        if (!task.getUser().getEmail().equals(userEmail)) {
            throw new TaskNotFoundException(id);
        }

        taskRepository.delete(task);
    }

    @Override
    public TaskResponse updateInfo(UUID id, UpdateTaskRequest request, String userEmail) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        if (!task.getUser().getEmail().equals(userEmail)) {
            throw new TaskNotFoundException(id);
        }

        if (request.title() != null) {
            task.setTitle(request.title());
        }

        if(request.description() != null) {
            task.setDescription(request.description());
        }

        if(request.priority() != null) {
            task.setPriority(request.priority());
        }

        if(request.dueDate() != null) {
            task.setDueDate(request.dueDate());
        }

        Task updated = taskRepository.save(task);
        return TaskResponse.fromEntity(updated);
    }

    @Override
    public TaskResponse changeStatus(UUID id, UpdateTaskStatusRequest request, String userEmail) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        if (!task.getUser().getEmail().equals(userEmail)) {
            throw new TaskNotFoundException(id);
        }

        task.setStatus(request.status());

        Task updated = taskRepository.save(task);
        return TaskResponse.fromEntity(updated);
    }
}
