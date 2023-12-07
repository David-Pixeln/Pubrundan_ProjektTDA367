package com.Pubrunda.entities.history.DTO.response;

import com.Pubrunda.entities.completedPubCrawl.CompletedPubCrawl;
import com.Pubrunda.entities.user.dto.response.UserDTO;
import lombok.Data;

@Data
public class HistoryDTO {
    private long id;
    private UserDTO user;
    private CompletedPubCrawl completedPubCrawl;
}
