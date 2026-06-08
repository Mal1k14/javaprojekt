package com.sport.sportsgroups.controller;

import com.sport.sportsgroups.entity.Photo;
import com.sport.sportsgroups.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    @Autowired
    private PhotoRepository photoRepository;

    @GetMapping
    public List<Photo> getAll() {
        return photoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Photo getById(@PathVariable Long id) {
        return photoRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Photo create(@RequestBody Photo photo) {
        return photoRepository.save(photo);
    }

    @PutMapping("/{id}")
    public Photo update(@PathVariable Long id, @RequestBody Photo updated) {
        Photo existing = photoRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setFileName(updated.getFileName());
            existing.setFilePath(updated.getFilePath());
            existing.setContentType(updated.getContentType());
            return photoRepository.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        photoRepository.deleteById(id);
    }
}