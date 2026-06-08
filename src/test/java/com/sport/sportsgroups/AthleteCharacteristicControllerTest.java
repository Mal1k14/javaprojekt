package com.sport.sportsgroups;

import com.sport.sportsgroups.controller.AthleteCharacteristicController;
import com.sport.sportsgroups.entity.AthleteCharacteristic;
import com.sport.sportsgroups.repository.AthleteCharacteristicRepository;
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


class AthleteCharacteristicControllerTest {

    @Mock
    private AthleteCharacteristicRepository repository;

    @InjectMocks
    private AthleteCharacteristicController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        AthleteCharacteristic ac = new AthleteCharacteristic();
        ac.setId(1L);
        ac.setValue(85);

        when(repository.findAll()).thenReturn(Arrays.asList(ac));

        List<AthleteCharacteristic> result = controller.getAll();

        assertEquals(1, result.size());
        assertEquals(85, result.get(0).getValue());
    }

    @Test
    void testGetById() {
        AthleteCharacteristic ac = new AthleteCharacteristic();
        ac.setId(1L);
        ac.setValue(90);

        when(repository.findById(1L)).thenReturn(Optional.of(ac));

        AthleteCharacteristic result = controller.getById(1L);

        assertNotNull(result);
        assertEquals(90, result.getValue());
    }

    @Test
    void testCreate() {
        AthleteCharacteristic ac = new AthleteCharacteristic();
        ac.setValue(75);

        when(repository.save(any(AthleteCharacteristic.class))).thenReturn(ac);

        AthleteCharacteristic result = controller.create(ac);

        assertNotNull(result);
    }

    @Test
    void testDelete() {
        controller.delete(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}