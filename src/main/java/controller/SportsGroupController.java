package com.sport.sportsgroups.controller;

import com.sport.sportsgroups.entity.SportsGroup;
import com.sport.sportsgroups.repository.SportsGroupRepository;
import org.slf4j.Logger;  // ← Новый импорт
import org.slf4j.LoggerFactory;  // ← Новый импорт
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class SportsGroupController {

    // Создаем логгер для этого класса
    private static final Logger logger = LoggerFactory.getLogger(SportsGroupController.class);

    @Autowired
    private SportsGroupRepository groupRepository;

    @GetMapping
    public List<SportsGroup> getAllGroups() {
        logger.info("Получение списка всех групп");  // ← Логируем
        return groupRepository.findAll();
    }

    @GetMapping("/{id}")
    public SportsGroup getGroupById(@PathVariable Long id) {
        logger.info("Поиск группы с ID: {}", id);  // {} - подставит значение id
        SportsGroup group = groupRepository.findById(id).orElse(null);
        if (group == null) {
            logger.warn("Группа с ID {} не найдена", id);  // ← Предупреждение
        } else {
            logger.debug("Найдена группа: {}", group.getName());  // ← Детальная информация
        }
        return group;
    }

    @PostMapping
    public SportsGroup createGroup(@RequestBody SportsGroup group) {
        logger.info("Создание новой группы: {}", group.getName());
        group.setCreatedAt(LocalDateTime.now());
        SportsGroup saved = groupRepository.save(group);
        logger.info("Группа создана с ID: {}", saved.getId());
        return saved;
    }

    @PutMapping("/{id}")
    public SportsGroup updateGroup(@PathVariable Long id, @RequestBody SportsGroup updatedGroup) {
        logger.info("Обновление группы с ID: {}", id);
        SportsGroup existingGroup = groupRepository.findById(id).orElse(null);
        if (existingGroup != null) {
            existingGroup.setName(updatedGroup.getName());
            existingGroup.setDescription(updatedGroup.getDescription());
            SportsGroup saved = groupRepository.save(existingGroup);
            logger.info("Группа обновлена: {}", saved.getName());
            return saved;
        }
        logger.warn("Группа для обновления не найдена: ID {}", id);
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable Long id) {
        logger.info("Удаление группы с ID: {}", id);
        groupRepository.deleteById(id);
        logger.info("Группа удалена: ID {}", id);
    }
}