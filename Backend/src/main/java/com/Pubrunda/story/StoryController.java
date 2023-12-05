package com.Pubrunda.story;

import com.Pubrunda.exception.ResourceNotFoundException;
import com.Pubrunda.pub.Pub;
import com.Pubrunda.repositories.StoryRepository;
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
        return repository.findById(storyId).orElseThrow(() -> new ResourceNotFoundException(storyId));
    }

    // CREATE
    @PostMapping
    public Story createStory(@RequestBody Story newStory) {
        return repository.save(newStory);
    }

    // DELETE
    @DeleteMapping("/{storyId}")
    public ResponseEntity<Pub> deleteStory(@PathVariable Long storyId) {
        Story existingStory = repository.findById(storyId).orElseThrow(() -> new ResourceNotFoundException(storyId));
        repository.delete(existingStory);
        return ResponseEntity.ok().build();
    }

}
