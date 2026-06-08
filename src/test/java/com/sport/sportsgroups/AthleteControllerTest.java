package com.sport.sportsgroups;

import com.sport.sportsgroups.controller.AthleteController;
import com.sport.sportsgroups.entity.Athlete;
import com.sport.sportsgroups.repository.AthleteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AthleteControllerTest {

    @Mock
    private AthleteRepository repository;

    @InjectMocks
    private AthleteController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Athlete athlete = new Athlete();
        athlete.setId(1L);
        athlete.setName("Иван");
        athlete.setBirthDate(LocalDate.of(2000, 1, 1));

        when(repository.findAll()).thenReturn(Arrays.asList(athlete));

        List<Athlete> result = controller.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Иван", result.get(0).getName());
    }

    @Test
    void testGetById() {
        Athlete athlete = new Athlete();
        athlete.setId(1L);
        athlete.setName("Иван");

        when(repository.findById(1L)).thenReturn(Optional.of(athlete));

        Athlete result = controller.getById(1L);

        assertNotNull(result);
        assertEquals("Иван", result.getName());
    }

    @Test
    void testCreate() {
        Athlete athlete = new Athlete();
        athlete.setName("Петр");

        when(repository.save(any(Athlete.class))).thenReturn(athlete);

        Athlete result = controller.create(athlete);

        assertNotNull(result);
        verify(repository, times(1)).save(any(Athlete.class));
    }

    @Test
    void testDelete() {
        controller.delete(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}