package com.backend.dto;

import com.backend.domain.task.TaskPriority;
import com.backend.domain.task.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskRequest(

        String title,
        String description,
        TaskPriority priority,
        TaskStatus status,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dueDate,

        UUID userId
) {
}
