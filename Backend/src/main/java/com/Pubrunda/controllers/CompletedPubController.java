package com.Pubrunda.controllers;

import com.Pubrunda.exception.ResourceNotFoundException;
import com.Pubrunda.models.CompletedPub;
import com.Pubrunda.pubs.Pub;
import com.Pubrunda.repositories.CompletedPubRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class CompletedPubController {

    private final CompletedPubRepository repository;

    CompletedPubController(CompletedPubRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<CompletedPub> getAllCompletedPubs() {
        return repository.findAll();
    }

    // READ
    @GetMapping("/{completedPubId}")
    public CompletedPub getCompletedPubById(@PathVariable long completedPubId) {
        return repository.findById(completedPubId).orElseThrow(() -> new ResourceNotFoundException(completedPubId));
    }

    // CREATE
    @PostMapping
    public CompletedPub createCompletedPub(@RequestBody CompletedPub newCompletedPub) {
        return repository.save(newCompletedPub);
    }

    // UPDATE
    @PutMapping("/{completedPubId}")
    public CompletedPub updateCompletedPubs(@RequestBody CompletedPub newCompletedPub, @PathVariable Long completedPubId) {
        // FIXME: Add all fields
        CompletedPub existingCompletedPubs = repository.findById(completedPubId).orElseThrow(() -> new ResourceNotFoundException(completedPubId));
        existingCompletedPubs.setCompletedAt(newCompletedPub.getCompletedAt());
        return repository.save(existingCompletedPubs);
    }

    // DELETE
    @DeleteMapping("/{completedPubId}")
    public ResponseEntity<Pub> deleteCompletedPub(@PathVariable Long completedPubId) {
        CompletedPub existingCompletedPubs = repository.findById(completedPubId).orElseThrow(() -> new ResourceNotFoundException(completedPubId));
        repository.delete(existingCompletedPubs);
        return ResponseEntity.ok().build();
    }
}
