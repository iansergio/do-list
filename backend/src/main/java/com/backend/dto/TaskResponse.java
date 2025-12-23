//package com.backend.dto;
//
//import com.backend.domain.task.Task;
//import com.backend.domain.task.TaskPriority;
//import com.backend.domain.task.TaskStatus;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//public record TaskResponse(
//        UUID id,
//        String title,
//        String description,
//        TaskPriority priority,
//        TaskStatus status,
//        LocalDateTime dueDate
//
//) {
//    public TaskResponse(Task task) {
//        this(task.getId(), task.getTitle(), task.getDescription(), task.getPriority(), task.getStatus(), task.getDueDate());
//    }
//}
