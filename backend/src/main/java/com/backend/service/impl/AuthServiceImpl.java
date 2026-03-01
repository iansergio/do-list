package com.backend.service.impl;

import com.backend.dto.response.AuthResponse;
import com.backend.dto.request.LoginRequest;
import com.backend.dto.request.RegisterRequest;
import com.backend.model.entity.RefreshToken;
import com.backend.model.enums.Role;
import com.backend.model.entity.User;
import com.backend.repository.UserRepository;
import com.backend.service.AuthService;
import com.backend.service.JwtService;
import com.backend.service.RefreshTokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(
            UserRepository userRepository,
            RefreshTokenService refreshTokenService,
            JwtService jwtService,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already registered");
        }

        // Cria novo usuário
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);

        userRepository.save(user);

        // Gera token
        String accessToken = jwtService.generateToken(user.getEmail(), user.getRole().name());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return new AuthResponse(
                accessToken,
                refreshToken.getToken(),
                user.getEmail(),
                user.getName(),
                "User registered"
        );
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        // Busca usuário
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        // Verifica senha
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Gera token
        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());
        RefreshToken refreshToken  = refreshTokenService.createRefreshToken(user.getId());

        return new AuthResponse(
                token,
                refreshToken.getToken(),
                user.getEmail(),
                user.getName(),
                "Logged in successfully");
    }

    @Override
    @Transactional
    public AuthResponse refresh(String tokenStr) {
        RefreshToken token = refreshTokenService
                .findByToken(tokenStr)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        refreshTokenService.verifyExpiration(token);

        User user = userRepository.findById(token.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Rotate refresh token: delete old one and create new one
        refreshTokenService.delete(token);
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user.getId());

        String newAccessToken = jwtService.generateToken(user.getEmail(), user.getRole().name());

        return new AuthResponse(
                newAccessToken,
                newRefreshToken.getToken(),
                user.getEmail(),
                user.getName(),
                "Token refreshed successfully"
        );
    }

    @Override
    @Transactional
    public void logout(String refreshTokenStr) {

        RefreshToken token = refreshTokenService
                .findByToken(refreshTokenStr)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        refreshTokenService.delete(token);
    }
}
