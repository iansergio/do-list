package com.backend.dto.request;

import com.backend.model.enums.Priority;

import java.time.LocalDateTime;

public record UpdateTaskRequest (
        String title,
        String description,
        Priority priority,
        LocalDateTime dueDate
) {
}
