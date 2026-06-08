package com.sport.sportsgroups.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component  // ← Говорит Spring: "Создай один экземпляр этого класса и управляй им"
public class JwtTokenProvider {

    @Value("${jwt.secret}")  // ← Берет значение из application.properties
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private SecretKey getSigningKey() {
        // Создаем секретный ключ из строки
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // Генерация токена для пользователя
    public String generateToken(String username, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .subject(username)  // Кто пользователь
                .claim("role", role)  // Его роль
                .issuedAt(now)  // Когда создан
                .expiration(expiryDate)  // Когда истечет
                .signWith(getSigningKey())  // Подпись ключом
                .compact();  // Превратить в строку
    }

    // Получить username из токена
    public String getUsernameFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    // Получить роль из токена
    public String getRoleFromToken(String token) {
        return parseClaims(token).get("role", String.class);
    }

    // Проверить, валиден ли токен
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}