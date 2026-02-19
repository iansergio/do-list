package com.backend.service;

import com.backend.model.entity.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(UUID userId);
    void verifyExpiration(RefreshToken token);
    Optional<RefreshToken> findByToken(String token);
    void delete(RefreshToken token);
}
