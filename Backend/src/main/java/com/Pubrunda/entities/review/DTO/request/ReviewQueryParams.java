package com.Pubrunda.entities.review.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewQueryParams {

    private Long authorId;
    private String authorUsername;
    private LocalDateTime before;
    private LocalDateTime after;

}
