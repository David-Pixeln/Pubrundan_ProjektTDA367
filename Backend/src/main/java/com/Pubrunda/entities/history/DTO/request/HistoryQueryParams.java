package com.Pubrunda.entities.history.DTO.request;

import com.Pubrunda.entities.completedPubCrawl.CompletedPubCrawl;
import com.Pubrunda.entities.user.User;
import lombok.Data;

@Data
public class HistoryQueryParams {
    private long id;
    private User user;
    private CompletedPubCrawl completedPubCrawl;
}
