package com.backend.dto.response;

import com.backend.model.entity.User;

import java.util.UUID;

public record UserResponse (
        UUID id,
        String email,
        String role
) {
    public static UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getRole().toString()
        );
    }
}
