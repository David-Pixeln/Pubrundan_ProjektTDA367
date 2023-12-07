package com.Pubrunda.entities.history;

import com.Pubrunda.dto.response.MessageResponse;
import com.Pubrunda.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.baseurl}/histories")
@RequiredArgsConstructor
public class HistoryController {

    private final ModelMapper modelMapper;
    private final HistoryService historyService;

    /*
    @GetMapping
    public List<CompletedPubCrawls> getAllCompletedPubCrawls() {
        return repository.findAll();
    }*/

    // READ
    @GetMapping("/{historyId}")
    public History getHistoryById(@PathVariable long historyId) {
        return historyService.getHistoryById(historyId);
    }

    // CREATE
    @PostMapping
    public History createHistory(@RequestBody History newHistory) {
        return historyService.createHistory(newHistory);
    }

    // UPDATE
    @PutMapping("/{historyId}")
    public History updateHistory(@RequestBody History newHistory, @PathVariable Long historyId) {
        return historyService.updateHistory(historyId, newHistory);
    }

    // DELETE
    @DeleteMapping("/{historyId}")
    public MessageResponse deleteHistory(@PathVariable Long historyId) {
        historyService.deleteHistory(historyId);
        return new MessageResponse("History deleted successfully");
    }
}
