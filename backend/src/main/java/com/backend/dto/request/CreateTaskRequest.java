package com.backend.dto.request;

import com.backend.model.entity.Task;
import com.backend.model.entity.User;
import com.backend.model.enums.Priority;
import com.backend.model.enums.Status;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record CreateTaskRequest(
        @NotBlank(message = "Title is required")
        String title,
        String description,
        Priority priority, // HIGH, MEDIUM, LOW
        Status status, // PENDING, COMPLETED
        LocalDateTime dueDate
) {
    public Task toEntity(User user) {
        return new Task(
                this.title,
                this.description,
                this.priority,
                this.status != null ? this.status : Status.PENDING,
                this.dueDate,
                user
        );
    }
}
