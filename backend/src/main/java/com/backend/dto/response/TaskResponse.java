package com.backend.dto.response;

import com.backend.model.entity.Task;
import com.backend.model.enums.Priority;
import com.backend.model.enums.Status;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskResponse(
        UUID id,
        String title,
        String description,
        Priority priority,
        Status status,
        LocalDateTime dueDate,
        UUID userId
) {
    public static TaskResponse fromEntity(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.getStatus(),
                task.getDueDate(),
                task.getUser().getId()
        );
    }
}
