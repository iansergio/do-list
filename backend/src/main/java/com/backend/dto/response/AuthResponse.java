package com.backend.dto.response;

public record AuthResponse (
    String accessToken,
    String refreshToken,
    String email,
    String name,
    String message
) {
}
