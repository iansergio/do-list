package com.backend.dto.request;

import com.backend.model.enums.Status;
import jakarta.validation.constraints.NotNull;

public record UpdateTaskStatusRequest (
        @NotNull(message = "Status is required")
        Status status
) {
}