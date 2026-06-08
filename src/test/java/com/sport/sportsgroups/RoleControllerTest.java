package com.sport.sportsgroups;

import com.sport.sportsgroups.controller.RoleController;
import com.sport.sportsgroups.entity.Role;
import com.sport.sportsgroups.repository.RoleRepository;
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

class RoleControllerTest {

    @Mock
    private RoleRepository repository;

    @InjectMocks
    private RoleController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");

        when(repository.findAll()).thenReturn(Arrays.asList(role));

        List<Role> result = controller.getAll();

        assertEquals(1, result.size());
        assertEquals("ADMIN", result.get(0).getName());
    }

    @Test
    void testGetById() {
        Role role = new Role();
        role.setId(1L);
        role.setName("COACH");

        when(repository.findById(1L)).thenReturn(Optional.of(role));

        Role result = controller.getById(1L);

        assertNotNull(result);
        assertEquals("COACH", result.getName());
    }

    @Test
    void testCreate() {
        Role role = new Role();
        role.setName("USER");

        when(repository.save(any(Role.class))).thenReturn(role);

        Role result = controller.create(role);

        assertNotNull(result);
    }

    @Test
    void testDelete() {
        controller.delete(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}