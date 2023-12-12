package com.Pubrunda.entities.story;

import com.Pubrunda.entities.pub.Pub;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stories")
public class StoryController {

    private final StoryRepository repository;

    StoryController(StoryRepository repository) {
        this.repository = repository;
    }

    // READ
    @GetMapping("/{storyId}")
    public Story getStoryById(@PathVariable long storyId) {
        return repository.findById(storyId).orElseThrow();
    }

    // CREATE
    @PostMapping
    public Story createStory(@RequestBody Story newStory) {
        return repository.save(newStory);
    }

    // DELETE
    @DeleteMapping("/{storyId}")
    public ResponseEntity<Pub> deleteStory(@PathVariable Long storyId) {
        Story existingStory = repository.findById(storyId).orElseThrow();
        repository.delete(existingStory);
        return ResponseEntity.ok().build();
    }

}
