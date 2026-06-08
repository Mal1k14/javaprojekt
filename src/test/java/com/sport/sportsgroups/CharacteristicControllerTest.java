package com.sport.sportsgroups;

import com.sport.sportsgroups.controller.CharacteristicController;
import com.sport.sportsgroups.entity.Characteristic;
import com.sport.sportsgroups.repository.CharacteristicRepository;
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

class CharacteristicControllerTest {

    @Mock
    private CharacteristicRepository repository;

    @InjectMocks
    private CharacteristicController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Characteristic c = new Characteristic();
        c.setId(1L);
        c.setName("Сила");

        when(repository.findAll()).thenReturn(Arrays.asList(c));

        List<Characteristic> result = controller.getAll();

        assertEquals(1, result.size());
        assertEquals("Сила", result.get(0).getName());
    }

    @Test
    void testGetById() {
        Characteristic c = new Characteristic();
        c.setId(1L);
        c.setName("Скорость");

        when(repository.findById(1L)).thenReturn(Optional.of(c));

        Characteristic result = controller.getById(1L);

        assertNotNull(result);
        assertEquals("Скорость", result.getName());
    }

    @Test
    void testCreate() {
        Characteristic c = new Characteristic();
        c.setName("Выносливость");

        when(repository.save(any(Characteristic.class))).thenReturn(c);

        Characteristic result = controller.create(c);

        assertNotNull(result);
    }

    @Test
    void testDelete() {
        controller.delete(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}