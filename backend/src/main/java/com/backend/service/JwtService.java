package com.backend.service;

import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;

public interface JwtService {
    String generateToken(String email);
    SecretKey getSignKey();
    String getEmailFromToken(String token);
    Claims getClaimsFromToken(String token);
    boolean validateToken(String token);
    boolean isTokenExpired(String token);
}
