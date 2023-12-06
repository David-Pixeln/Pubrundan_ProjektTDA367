package com.Pubrunda.entities.post.dto;

import com.Pubrunda.entities.user.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostQueryParams {

    private long id;
    private User author;
    private LocalDateTime timestamp;
}
