package com.sport.sportsgroups;

import com.sport.sportsgroups.controller.UserController;
import com.sport.sportsgroups.entity.User;
import com.sport.sportsgroups.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        User user = new User();
        user.setId(1L);
        user.setUsername("admin");

        when(repository.findAll()).thenReturn(Arrays.asList(user));

        List<User> result = controller.getAll();

        assertEquals(1, result.size());
        assertEquals("admin", result.get(0).getUsername());
    }

    @Test
    void testGetById() {
        User user = new User();
        user.setId(1L);
        user.setUsername("coach");

        when(repository.findById(1L)).thenReturn(Optional.of(user));

        User result = controller.getById(1L);

        assertNotNull(result);
        assertEquals("coach", result.getUsername());
    }

    @Test
    void testCreate() {
        User user = new User();
        user.setUsername("new_user");
        user.setPassword("pass");

        when(repository.save(any(User.class))).thenReturn(user);

        User result = controller.create(user);

        assertNotNull(result);
    }

    @Test
    void testDelete() {
        controller.delete(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}