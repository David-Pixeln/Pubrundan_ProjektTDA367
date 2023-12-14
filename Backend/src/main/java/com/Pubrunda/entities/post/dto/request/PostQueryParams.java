package com.Pubrunda.entities.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostQueryParams {

    private Long id;
    private Long authorId;
    private String authorUsername;
    private LocalDateTime after;
    private LocalDateTime before;

}
