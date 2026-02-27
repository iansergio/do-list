package com.backend.service;

import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;

public interface JwtService {
    String generateToken(String email);
    SecretKey findSignKey();
    String findEmailFromToken(String token);
    String findRoleFromToken(String token);
    Claims findClaimsFromToken(String token);
    boolean validateToken(String token);
}
