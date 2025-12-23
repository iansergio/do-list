package com.backend.controller;

import com.backend.domain.task.Task;
import com.backend.dto.TaskRequest;
import com.backend.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Task> save(@Valid @RequestBody TaskRequest request) {
        Task task = service.save(request);
        URI location = URI.create("/api/tasks/" + task.getId());

        return ResponseEntity.created(location).body(task);
    }
}
