package com.Pubrunda.entities.history;

import com.Pubrunda.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/histories")
public class HistoryController {

    private final HistoryRepository repository;

    HistoryController(HistoryRepository repository) {
        this.repository = repository;
    }

    /*
    @GetMapping
    public List<CompletedPubCrawls> getAllCompletedPubCrawls() {
        return repository.findAll();
    }*/

    // READ
    @GetMapping("/{historyId}")
    public History getHistoryById(@PathVariable long historyId) {
        return repository.findById(historyId).orElseThrow(() -> new ResourceNotFoundException(historyId));
    }

    // CREATE
    @PostMapping
    public History createHistory(@RequestBody History newHistory) {
        return repository.save(newHistory);
    }

    // UPDATE
    @PutMapping("/{historyId}")
    public History updateHistory(@RequestBody History newHistory, @PathVariable Long historyId) {
        History existingHistory = repository.findById(historyId).orElseThrow(() -> new ResourceNotFoundException(historyId));
        //existingHistory.setName(newHistory.getAllCompletedPubCrawls());
        return repository.save(existingHistory);
    }

    // DELETE
    @DeleteMapping("/{historyId}")
    public ResponseEntity<History> deleteHistory(@PathVariable Long historyId) {
        History existingHistory = repository.findById(historyId).orElseThrow(() -> new ResourceNotFoundException(historyId));
        repository.delete(existingHistory);
        return ResponseEntity.ok().build();
    }
}
