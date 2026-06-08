package com.sport.sportsgroups;

import com.sport.sportsgroups.controller.AuthController;
import com.sport.sportsgroups.dto.LoginRequest;
import com.sport.sportsgroups.dto.RegisterRequest;
import com.sport.sportsgroups.entity.Role;
import com.sport.sportsgroups.entity.User;
import com.sport.sportsgroups.repository.RoleRepository;
import com.sport.sportsgroups.repository.UserRepository;
import com.sport.sportsgroups.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider tokenProvider;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterSuccess() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("test_user");
        request.setPassword("password123");
        request.setEmail("test@example.com");

        when(userRepository.existsByUsername("test_user")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encoded_password");

        Role coachRole = new Role();
        coachRole.setName("COACH");
        when(roleRepository.findByName("COACH")).thenReturn(Optional.of(coachRole));

        ResponseEntity<?> response = authController.register(request);

        assertEquals(200, response.getStatusCode().value());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUsernameExists() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("existing_user");
        request.setPassword("password123");
        request.setEmail("test@example.com");

        when(userRepository.existsByUsername("existing_user")).thenReturn(true);

        ResponseEntity<?> response = authController.register(request);

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void testLoginSuccess() {
        LoginRequest request = new LoginRequest();
        request.setUsername("test_user");
        request.setPassword("password123");

        User user = new User();
        user.setUsername("test_user");
        user.setPassword("encoded_password");
        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setName("COACH");
        roles.add(role);
        user.setRoles(roles);

        when(userRepository.findByUsername("test_user")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "encoded_password")).thenReturn(true);
        when(tokenProvider.generateToken("test_user", "COACH")).thenReturn("fake_token");

        ResponseEntity<?> response = authController.login(request);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void testLoginUserNotFound() {
        LoginRequest request = new LoginRequest();
        request.setUsername("unknown_user");
        request.setPassword("password123");

        when(userRepository.findByUsername("unknown_user")).thenReturn(Optional.empty());

        ResponseEntity<?> response = authController.login(request);

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void testLoginWrongPassword() {
        LoginRequest request = new LoginRequest();
        request.setUsername("test_user");
        request.setPassword("wrong_password");

        User user = new User();
        user.setUsername("test_user");
        user.setPassword("encoded_password");

        when(userRepository.findByUsername("test_user")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong_password", "encoded_password")).thenReturn(false);

        ResponseEntity<?> response = authController.login(request);

        assertEquals(400, response.getStatusCode().value());
    }
}