package com.backend.service.impl;

import com.backend.model.entity.RefreshToken;
import com.backend.repository.RefreshTokenRepository;
import com.backend.service.RefreshTokenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${jwt.refresh.expiration}")
    private int refreshTokenExpirationTime;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    @Transactional
    public RefreshToken createRefreshToken(UUID userId) {
        refreshTokenRepository.deleteByUserId(userId);

        RefreshToken token = new RefreshToken();
        token.setUserId(userId);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiresAt(LocalDateTime.now().plusDays(refreshTokenExpirationTime));

        return refreshTokenRepository.save(token);
    }

    @Override
    @Transactional
    public void verifyExpiration(RefreshToken token) {
        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.deleteByToken(token.getToken());
            throw new RuntimeException("Refresh token expired. Log in again to get a new one.");
        }
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    @Transactional
    public void delete(RefreshToken token) {
        refreshTokenRepository.delete(token);
    }
}
