package com.backend.service.impl;

import com.backend.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    // Variável de ambiente com a chave secreta
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private long expirationTime;

    @Override
    public SecretKey findSignKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(findSignKey())
                .compact();
    }

    @Override
    public String findEmailFromToken(String token) {
        return findClaimsFromToken(token).getSubject();
    }

    @Override
    public String findRoleFromToken(String token) {
        return findClaimsFromToken(token).get("role", String.class);
    }

    @Override
    public Claims findClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(findSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            findClaimsFromToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
