package com.sport.sportsgroups.controller;

import org.springframework.http.ResponseEntity;
import com.sport.sportsgroups.entity.Athlete;
import com.sport.sportsgroups.entity.SportsGroup;  // ← ДОБАВИТЬ ЭТО!
import com.sport.sportsgroups.repository.AthleteRepository;
import com.sport.sportsgroups.repository.SportsGroupRepository;  // ← И ЭТО, если используете
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/athletes")
public class AthleteController {

    private static final Logger logger = LoggerFactory.getLogger(AthleteController.class);

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private SportsGroupRepository groupRepository;  // ← Если используется

    @GetMapping
    public List<Athlete> getAll() {
        logger.info("Получение списка всех спортсменов");
        return athleteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Athlete getById(@PathVariable Long id) {
        logger.info("Поиск спортсмена с ID: {}", id);
        return athleteRepository.findById(id).orElse(null);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Athlete athlete) {
        try {
            logger.info("Создание нового спортсмена: {}", athlete.getName());

            if (athlete.getGroup() != null && athlete.getGroup().getId() != null) {
                SportsGroup group = groupRepository.findById(athlete.getGroup().getId()).orElse(null);
                athlete.setGroup(group);
                logger.info("Назначена группа: {}", group != null ? group.getName() : "null");
            }

            Athlete saved = athleteRepository.save(athlete);
            logger.info("Спортсмен создан с ID: {}", saved.getId());

            // Возвращаем только ID и имя, без всего объекта
            return ResponseEntity.ok(java.util.Map.of("id", saved.getId(), "name", saved.getName()));
        } catch (Exception e) {
            logger.error("Ошибка: ", e);
            return ResponseEntity.status(500).body("Ошибка: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Athlete update(@PathVariable Long id, @RequestBody Athlete updated) {
        logger.info("Обновление спортсмена с ID: {}", id);
        Athlete existing = athleteRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(updated.getName());
            existing.setBirthDate(updated.getBirthDate());
            if (updated.getGroup() != null && updated.getGroup().getId() != null) {
                SportsGroup group = groupRepository.findById(updated.getGroup().getId()).orElse(null);
                existing.setGroup(group);
            }
            return athleteRepository.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        logger.info("Удаление спортсмена с ID: {}", id);
        athleteRepository.deleteById(id);
    }
}