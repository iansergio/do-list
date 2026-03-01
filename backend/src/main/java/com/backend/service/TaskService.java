package com.backend.service;

import com.backend.dto.request.CreateTaskRequest;
import com.backend.dto.response.TaskResponse;
import com.backend.dto.request.UpdateTaskRequest;
import com.backend.dto.request.UpdateTaskStatusRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    TaskResponse save(CreateTaskRequest request, String userEmail);
    List<TaskResponse> findAll();
    List<TaskResponse> findAllByUser(String userEmail);
    Optional<TaskResponse> findById(UUID id);
    Optional<TaskResponse> findByIdAndUser(UUID id, String userEmail);
    void delete(UUID id, String userEmail);
    TaskResponse updateInfo(UUID id, UpdateTaskRequest request, String userEmail);
    TaskResponse changeStatus(UUID id, UpdateTaskStatusRequest request, String userEmail);
}
