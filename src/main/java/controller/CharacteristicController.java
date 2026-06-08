package com.sport.sportsgroups.controller;

import com.sport.sportsgroups.entity.Characteristic;
import com.sport.sportsgroups.repository.CharacteristicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/characteristics")
public class CharacteristicController {

    @Autowired
    private CharacteristicRepository repository;

    @GetMapping
    public List<Characteristic> getAll() { return repository.findAll(); }

    @GetMapping("/{id}")
    public Characteristic getById(@PathVariable Long id) { return repository.findById(id).orElse(null); }

    @PostMapping
    public Characteristic create(@RequestBody Characteristic entity) { return repository.save(entity); }

    @PutMapping("/{id}")
    public Characteristic update(@PathVariable Long id, @RequestBody Characteristic updated) {
        Characteristic existing = repository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(updated.getName());
            existing.setDescription(updated.getDescription());
            return repository.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { repository.deleteById(id); }
}