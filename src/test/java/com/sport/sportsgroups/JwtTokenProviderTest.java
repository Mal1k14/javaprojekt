package com.sport.sportsgroups;

import com.sport.sportsgroups.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        tokenProvider = new JwtTokenProvider();
        // Устанавливаем значения полей через рефлексию (т.к. они @Value)
        ReflectionTestUtils.setField(tokenProvider, "jwtSecret",
                "MySuperSecretKeyForJWTTokenGeneration12345678901234567890");
        ReflectionTestUtils.setField(tokenProvider, "jwtExpiration", 86400000L);
    }

    @Test
    void testGenerateToken() {
        String token = tokenProvider.generateToken("test_user", "COACH");
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void testGetUsernameFromToken() {
        String token = tokenProvider.generateToken("test_user", "COACH");
        String username = tokenProvider.getUsernameFromToken(token);
        assertEquals("test_user", username);
    }

    @Test
    void testGetRoleFromToken() {
        String token = tokenProvider.generateToken("test_user", "COACH");
        String role = tokenProvider.getRoleFromToken(token);
        assertEquals("COACH", role);
    }

    @Test
    void testValidateToken() {
        String token = tokenProvider.generateToken("test_user", "COACH");
        assertTrue(tokenProvider.validateToken(token));
    }

    @Test
    void testValidateInvalidToken() {
        assertFalse(tokenProvider.validateToken("invalid_token"));
    }

    @Test
    void testValidateNullToken() {
        assertFalse(tokenProvider.validateToken(null));
    }
}