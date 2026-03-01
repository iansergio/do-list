package com.backend.controller;

import com.backend.dto.request.CreateTaskRequest;
import com.backend.dto.response.TaskResponse;
import com.backend.dto.request.UpdateTaskRequest;
import com.backend.dto.request.UpdateTaskStatusRequest;
import com.backend.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@AuthenticationPrincipal String userEmail, @Valid @RequestBody CreateTaskRequest request) {
        TaskResponse savedTask = service.save(request, userEmail);
        URI location = URI.create("/api/tasks/" + savedTask.id());
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(location)
                .body(savedTask);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAll(@AuthenticationPrincipal String userEmail) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findAllByUser(userEmail));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getById(@AuthenticationPrincipal String userEmail, @PathVariable UUID id) {
        return service.findByIdAndUser(id, userEmail)
                .map(entity -> ResponseEntity.status(HttpStatus.OK).body(entity))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@AuthenticationPrincipal String userEmail, @PathVariable UUID id) {
        service.delete(id, userEmail);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}/infos")
    public ResponseEntity<TaskResponse> update(
            @AuthenticationPrincipal String userEmail,
            @PathVariable UUID id,
            @Valid @RequestBody UpdateTaskRequest request
    ) {
        TaskResponse updated = service.updateInfo(id, request, userEmail);
        return ResponseEntity.status(HttpStatus.OK)
                .body(updated);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> patchStatus(
            @AuthenticationPrincipal String userEmail,
            @PathVariable UUID id,
            @Valid @RequestBody UpdateTaskStatusRequest request
    ) {
        TaskResponse updated = service.changeStatus(id, request, userEmail);
        return ResponseEntity.status(HttpStatus.OK)
                .body(updated);
    }

}
