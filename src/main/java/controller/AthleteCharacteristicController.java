package com.sport.sportsgroups.controller;

import com.sport.sportsgroups.entity.AthleteCharacteristic;
import com.sport.sportsgroups.entity.Athlete;
import com.sport.sportsgroups.entity.Characteristic;
import com.sport.sportsgroups.repository.AthleteCharacteristicRepository;
import com.sport.sportsgroups.repository.AthleteRepository;
import com.sport.sportsgroups.repository.CharacteristicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/athlete-characteristics")
public class AthleteCharacteristicController {

    private static final Logger logger = LoggerFactory.getLogger(AthleteCharacteristicController.class);

    @Autowired
    private AthleteCharacteristicRepository acRepository;

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private CharacteristicRepository characteristicRepository;

    @GetMapping
    public List<AthleteCharacteristic> getAll() {
        logger.info("Получение списка всех значений характеристик");
        return acRepository.findAll();
    }

    @GetMapping("/{id}")
    public AthleteCharacteristic getById(@PathVariable Long id) {
        logger.info("Поиск значения характеристики с ID: {}", id);
        return acRepository.findById(id).orElse(null);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> requestData) {
        try {
            logger.info("Создание значения характеристики: {}", requestData);

            Long athleteId = null;
            Long characteristicId = null;
            Integer value = null;

            Object athleteObj = requestData.get("athlete");
            if (athleteObj instanceof Map) {
                Map<?, ?> athleteMap = (Map<?, ?>) athleteObj;
                Object athleteIdObj = athleteMap.get("id");
                if (athleteIdObj instanceof Number) {
                    athleteId = ((Number) athleteIdObj).longValue();
                }
            }

            Object characteristicObj = requestData.get("characteristic");
            if (characteristicObj instanceof Map) {
                Map<?, ?> characteristicMap = (Map<?, ?>) characteristicObj;
                Object characteristicIdObj = characteristicMap.get("id");
                if (characteristicIdObj instanceof Number) {
                    characteristicId = ((Number) characteristicIdObj).longValue();
                }
            }

            Object valueObj = requestData.get("value");
            if (valueObj instanceof Number) {
                value = ((Number) valueObj).intValue();
            }

            if (athleteId == null || characteristicId == null || value == null) {
                return ResponseEntity.badRequest().body("Не все поля заполнены");
            }

            final Long finalAthleteId = athleteId;
            final Long finalCharacteristicId = characteristicId;
            final Integer finalValue = value;

            Athlete athlete = athleteRepository.findById(finalAthleteId)
                    .orElseThrow(() -> new RuntimeException("Спортсмен не найден"));

            Characteristic characteristic = characteristicRepository.findById(finalCharacteristicId)
                    .orElseThrow(() -> new RuntimeException("Характеристика не найдена"));

            AthleteCharacteristic ac = new AthleteCharacteristic();
            ac.setAthlete(athlete);
            ac.setCharacteristic(characteristic);
            ac.setValue(finalValue);

            AthleteCharacteristic saved = acRepository.save(ac);
            logger.info("Значение характеристики создано с ID: {}", saved.getId());

            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            logger.error("Ошибка при создании: ", e);
            return ResponseEntity.status(500).body("Ошибка: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        logger.info("Удаление значения характеристики с ID: {}", id);
        acRepository.deleteById(id);
    }
}