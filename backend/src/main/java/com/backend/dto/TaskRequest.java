package com.backend.dto;

import com.backend.domain.task.TaskPriority;
import com.backend.domain.task.TaskStatus;
import com.backend.domain.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskRequest(

        String title,
        String description,
        TaskPriority priority,
        TaskStatus status,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dueDate

        private User user;

) {
}
