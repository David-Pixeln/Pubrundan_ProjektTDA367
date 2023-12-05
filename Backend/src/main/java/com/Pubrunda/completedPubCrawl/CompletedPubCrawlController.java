package com.Pubrunda.completedPubCrawl;

import com.Pubrunda.exception.ResourceNotFoundException;
import com.Pubrunda.leaderboard.Leaderboard;
import com.Pubrunda.repositories.CompletedPubCrawlRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/completedPubCrawls")
public class CompletedPubCrawlController {

    private final CompletedPubCrawlRepository repository;

    CompletedPubCrawlController(CompletedPubCrawlRepository repository) {
        this.repository = repository;
    }

    // READ
    @GetMapping("/{completedPubCrawlId}")
    public CompletedPubCrawl getCompletedPubCrawlById(@PathVariable long completedPubCrawlId) {
        return repository.findById(completedPubCrawlId).orElseThrow(() -> new ResourceNotFoundException(completedPubCrawlId));
    }

    // CREATE
    @PostMapping
    public CompletedPubCrawl createCompletedPubCrawl(@RequestBody CompletedPubCrawl newCompletedPubCrawl) {
        return repository.save(newCompletedPubCrawl);
    }

    // UPDATE
    @PutMapping("/{completedPubCrawlId}")
    public CompletedPubCrawl updateCompletedPubCrawl(@RequestBody Leaderboard newCompletedPubCrawl, @PathVariable Long completedPubCrawlId) {
        CompletedPubCrawl existingCompletedPubCrawl = repository.findById(completedPubCrawlId).orElseThrow(() -> new ResourceNotFoundException(completedPubCrawlId));
        return repository.save(existingCompletedPubCrawl);
    }

    // DELETE
    @DeleteMapping("/{completedPubCrawlId}")
    public ResponseEntity<CompletedPubCrawl> deleteCompletedPubCrawl(@PathVariable Long completedPubCrawlId) {
        CompletedPubCrawl existingCompletedPubCrawl = repository.findById(completedPubCrawlId).orElseThrow(() -> new ResourceNotFoundException(completedPubCrawlId));
        repository.delete(existingCompletedPubCrawl);
        return ResponseEntity.ok().build();
    }

}
