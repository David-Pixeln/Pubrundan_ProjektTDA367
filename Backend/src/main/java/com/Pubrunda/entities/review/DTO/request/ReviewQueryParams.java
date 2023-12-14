package com.Pubrunda.entities.review.DTO.request;

import com.Pubrunda.entities.user.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewQueryParams {

    private long id;
    private Long authorId;
    private Long authorUsername;
    private LocalDateTime createdAt;

}
