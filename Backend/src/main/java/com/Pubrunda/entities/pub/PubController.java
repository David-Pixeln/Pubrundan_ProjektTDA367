package com.Pubrunda.entities.pub;

import com.Pubrunda.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pubs")
public class PubController {

    private final PubRepository repository;

    PubController(PubRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Pub> getAllPubs() {
        return repository.findAll();
    }

    // READ
    @GetMapping("/{pubId}")
    public Pub getPubById(@PathVariable long pubId) {
        return repository.findById(pubId).orElseThrow(() -> new ResourceNotFoundException(pubId));
    }

    // CREATE
    @PostMapping("")
    public Pub createPub(@RequestBody Pub newPub) {
        return repository.save(newPub);
    }

    // UPDATE
    @PutMapping("/{pubId}")
    public Pub updatePub(@RequestBody Pub newPub, @PathVariable Long pubId) {
        Pub existingPub = repository.findById(pubId).orElseThrow(() -> new ResourceNotFoundException(pubId));
        existingPub.setName(newPub.getName());
        existingPub.setOpeningTime(newPub.getOpeningTime());
        existingPub.setClosingTime(newPub.getClosingTime());
        return repository.save(existingPub);
    }

    // DELETE
    @DeleteMapping("/{pubId}")
    public ResponseEntity<Pub> deletePub(@PathVariable Long pubId) {
        Pub existingPub = repository.findById(pubId).orElseThrow(() -> new ResourceNotFoundException(pubId));
        repository.delete(existingPub);
        return ResponseEntity.ok().build();
    }

}
