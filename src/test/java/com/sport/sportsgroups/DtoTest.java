package com.sport.sportsgroups;

import com.sport.sportsgroups.dto.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DtoTest {

    @Test
    void testLoginRequest() {
        LoginRequest request = new LoginRequest();
        request.setUsername("test");
        request.setPassword("pass");

        assertEquals("test", request.getUsername());
        assertEquals("pass", request.getPassword());
    }

    @Test
    void testRegisterRequest() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("test");
        request.setPassword("pass");
        request.setEmail("test@example.com");

        assertEquals("test", request.getUsername());
        assertEquals("pass", request.getPassword());
        assertEquals("test@example.com", request.getEmail());
    }

    @Test
    void testAuthResponse() {
        AuthResponse response = new AuthResponse("token123", "user1", "COACH");

        assertEquals("token123", response.getToken());
        assertEquals("user1", response.getUsername());
        assertEquals("COACH", response.getRole());
    }
}