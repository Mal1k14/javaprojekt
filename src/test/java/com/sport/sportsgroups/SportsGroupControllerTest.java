package com.sport.sportsgroups;

import com.sport.sportsgroups.controller.SportsGroupController;
import com.sport.sportsgroups.entity.SportsGroup;
import com.sport.sportsgroups.repository.SportsGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SportsGroupControllerTest {

    @Mock
    private SportsGroupRepository repository;

    @InjectMocks
    private SportsGroupController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllGroups() {
        SportsGroup group = new SportsGroup();
        group.setId(1L);
        group.setName("Бокс");
        group.setCreatedAt(LocalDateTime.now());

        when(repository.findAll()).thenReturn(Arrays.asList(group));

        List<SportsGroup> result = controller.getAllGroups();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Бокс", result.get(0).getName());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetGroupById() {
        SportsGroup group = new SportsGroup();
        group.setId(1L);
        group.setName("Бокс");

        when(repository.findById(1L)).thenReturn(Optional.of(group));

        SportsGroup result = controller.getGroupById(1L);

        assertNotNull(result);
        assertEquals("Бокс", result.getName());
    }

    @Test
    void testCreateGroup() {
        SportsGroup group = new SportsGroup();
        group.setName("Новая группа");
        group.setCreatedAt(LocalDateTime.now());

        when(repository.save(any(SportsGroup.class))).thenReturn(group);

        SportsGroup result = controller.createGroup(group);

        assertNotNull(result);
        assertEquals("Новая группа", result.getName());
        verify(repository, times(1)).save(any(SportsGroup.class));
    }

    @Test
    void testDeleteGroup() {
        controller.deleteGroup(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}