package com.Pubrunda.controllers;

import com.Pubrunda.exception.ResourceNotFoundException;
import com.Pubrunda.models.AchievementSet;
import com.Pubrunda.repositories.AchievementSetRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/achievementSet")
public class AchievementSetController {

    private final AchievementSetRepository repository;

    AchievementSetController(AchievementSetRepository repository) {
        this.repository = repository;
    }

    // READ
    @GetMapping("/{achievementSetId}")
    public AchievementSet getAchievementSetById(@PathVariable long achievementSetId) {
        return repository.findById(achievementSetId).orElseThrow(() -> new ResourceNotFoundException(achievementSetId));
    }

    // CREATE
    @PostMapping
    public AchievementSet createAchievementSet(@RequestBody AchievementSet newAchievementSet) {
        return repository.save(newAchievementSet);
    }

    // UPDATE
    @PutMapping("/{achievementSetId}")
    public AchievementSet updateAchievementSet(@RequestBody AchievementSet newAchievementSet, @PathVariable Long achievementSetId) {
        AchievementSet existingAchievementSet = repository.findById(achievementSetId).orElseThrow(() -> new ResourceNotFoundException(achievementSetId));
        return repository.save(existingAchievementSet);
    }

    // DELETE
    @DeleteMapping("/{achievementSetId}")
    public ResponseEntity<AchievementSet> deleteAchievementSet(@PathVariable Long achievementSetId) {
        AchievementSet existingAchievementSet = repository.findById(achievementSetId).orElseThrow(() -> new ResourceNotFoundException(achievementSetId));
        repository.delete(existingAchievementSet);
        return ResponseEntity.ok().build();
    }
}
