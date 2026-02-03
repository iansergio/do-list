package com.backend.dto;

import com.backend.entity.task.TaskPriority;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateTaskInfosRequest {

    private String title;
    private String description;
    private TaskPriority priority;
    private LocalDateTime dueDate;
}
