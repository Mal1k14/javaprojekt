package com.sport.sportsgroups;

import com.sport.sportsgroups.entity.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    @Test
    void testUserEntity() {
        User user = new User();
        user.setId(1L);
        user.setUsername("test_user");
        user.setPassword("password");
        user.setEmail("test@example.com");

        assertEquals(1L, user.getId());
        assertEquals("test_user", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void testRoleEntity() {
        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");

        assertEquals("ADMIN", role.getName());
    }

    @Test
    void testSportsGroupEntity() {
        SportsGroup group = new SportsGroup();
        group.setId(1L);
        group.setName("Бокс");
        group.setDescription("Группа по боксу");
        group.setCreatedAt(LocalDateTime.now());

        assertEquals("Бокс", group.getName());
        assertNotNull(group.getCreatedAt());
    }

    @Test
    void testAthleteEntity() {
        Athlete athlete = new Athlete();
        athlete.setId(1L);
        athlete.setName("Иван");
        athlete.setBirthDate(LocalDate.of(2000, 1, 1));

        assertEquals("Иван", athlete.getName());
    }

    @Test
    void testCharacteristicEntity() {
        Characteristic characteristic = new Characteristic();
        characteristic.setId(1L);
        characteristic.setName("Сила");
        characteristic.setDescription("Физическая сила");

        assertEquals("Сила", characteristic.getName());
    }

    @Test
    void testAthleteCharacteristicEntity() {
        AthleteCharacteristic ac = new AthleteCharacteristic();
        ac.setId(1L);
        ac.setValue(85);

        assertEquals(85, ac.getValue());
    }

    @Test
    void testPhotoEntity() {
        Photo photo = new Photo();
        photo.setId(1L);
        photo.setFileName("test.jpg");
        photo.setFilePath("/images/test.jpg");
        photo.setContentType("image/jpeg");

        assertEquals("test.jpg", photo.getFileName());
        assertEquals("image/jpeg", photo.getContentType());
    }

    @Test
    void testUserRolesRelationship() {
        User user = new User();
        Role role = new Role();
        role.setName("COACH");

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        assertEquals(1, user.getRoles().size());
    }
}