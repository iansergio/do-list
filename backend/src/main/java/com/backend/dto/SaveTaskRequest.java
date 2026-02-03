package com.backend.dto;

import com.backend.entity.task.TaskPriority;
import com.backend.entity.task.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class SaveTaskRequest {

    @NotNull(message = "Title cannot be null")
    private String title;

    @NotNull(message = "Description cannot be null")
    private String description;

    private TaskPriority priority; // HIGH, MEDIUM, LOW
    private TaskStatus status; // PENDING, FINISHED

    @NotNull(message = "Date cannot be null")
    LocalDateTime dueDate;

    @NotNull(message = "User id cannot be null")
    private UUID userId;
}
