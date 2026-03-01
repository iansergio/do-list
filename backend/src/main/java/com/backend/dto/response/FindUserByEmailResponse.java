package com.backend.dto.response;

import com.backend.model.entity.User;

import java.util.List;
import java.util.UUID;

public record FindUserByEmailResponse (
        UUID id,
        String email,
        String role,
        List<TaskResponse> tasks
) {
    public static FindUserByEmailResponse fromEntity(User user) {
        return new FindUserByEmailResponse(
                user.getId(),
                user.getEmail(),
                user.getRole().toString(),
                user.getTasks().stream()
                        .map(TaskResponse::fromEntity)
                        .toList()
        );
    }
}
