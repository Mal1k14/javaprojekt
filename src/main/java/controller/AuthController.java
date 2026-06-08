package com.sport.sportsgroups.controller;

import com.sport.sportsgroups.dto.AuthResponse;
import com.sport.sportsgroups.dto.LoginRequest;
import com.sport.sportsgroups.dto.RegisterRequest;
import com.sport.sportsgroups.entity.Role;
import com.sport.sportsgroups.entity.User;
import com.sport.sportsgroups.repository.RoleRepository;
import com.sport.sportsgroups.repository.UserRepository;
import com.sport.sportsgroups.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    // Регистрация нового пользователя
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // Проверяем, не занят ли username
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username уже занят");
        }

        // Создаем пользователя
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));  // Шифруем пароль!
        user.setEmail(request.getEmail());

        // Назначаем роль COACH по умолчанию
        Role coachRole = roleRepository.findByName("COACH")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("COACH");
                    return roleRepository.save(newRole);
                });

        Set<Role> roles = new HashSet<>();
        roles.add(coachRole);
        user.setRoles(roles);

        userRepository.save(user);

        return ResponseEntity.ok("Пользователь зарегистрирован");
    }

    // Вход пользователя
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Ищем пользователя
        User user = userRepository.findByUsername(request.getUsername())
                .orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body("Пользователь не найден");
        }

        // Проверяем пароль
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Неверный пароль");
        }

        // Получаем роль пользователя
        String role = user.getRoles().stream()
                .findFirst()
                .map(Role::getName)
                .orElse("USER");

        // Генерируем токен
        String token = tokenProvider.generateToken(user.getUsername(), role);

        return ResponseEntity.ok(new AuthResponse(token, user.getUsername(), role));
    }
}