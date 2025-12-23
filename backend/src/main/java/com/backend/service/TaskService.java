package com.backend.service;

import com.backend.domain.task.Task;
import com.backend.dto.TaskRequest;

public interface TaskService {
    Task save(TaskRequest request);
}
