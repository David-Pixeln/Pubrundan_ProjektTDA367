package com.Pubrunda.entities.history;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;

    public History getHistoryById(long postId) {
        return historyRepository.findById(postId).orElseThrow();
    }

    public History createHistory(History newPost) {
        return historyRepository.save(newPost);
    }

    public void deleteHistory(long postId) {
        historyRepository.deleteById(postId);
    }

    public History updateHistory(long historyId, History newHistory) {
        History existingHistory = historyRepository.getById(historyId);
        existingHistory.setUser(newHistory.getUser());
        existingHistory.setCompletedPubCrawlList(newHistory.getCompletedPubCrawlList());
        return historyRepository.save(existingHistory);
    }
}
