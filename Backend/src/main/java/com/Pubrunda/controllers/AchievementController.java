package com.Pubrunda.controllers;

import com.Pubrunda.exception.ResourceNotFoundException;
import com.Pubrunda.models.AchievementSet;
import com.Pubrunda.repositories.AchievementRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/achievements")
public class AchievementController {

    private final AchievementRepository repository;

    AchievementController(AchievementRepository repository) {
        this.repository = repository;
    }

    // READ
    @GetMapping("/{achievementId}")
    public AchievementSet getAchievementById(@PathVariable long achievementId) {
        return repository.findById(achievementId).orElseThrow(() -> new ResourceNotFoundException(achievementId));
    }

    // CREATE
    @PostMapping
    public AchievementSet createAchievement(@RequestBody AchievementSet newAchievement) {
        return repository.save(newAchievement);
    }

    // UPDATE
    @PutMapping("/{achievementId}")
    public AchievementSet updateAchievement(@RequestBody AchievementSet newAchievement, @PathVariable Long achievementId) {
        AchievementSet existingAchievement = repository.findById(achievementId).orElseThrow(() -> new ResourceNotFoundException(achievementId));
        return repository.save(existingAchievement);
    }

    // DELETE
    @DeleteMapping("/{achievementId}")
    public ResponseEntity<AchievementSet> deleteAchievement(@PathVariable Long achievementId) {
        AchievementSet existingAchievement = repository.findById(achievementId).orElseThrow(() -> new ResourceNotFoundException(achievementId));
        repository.delete(existingAchievement);
        return ResponseEntity.ok().build();
    }


}
