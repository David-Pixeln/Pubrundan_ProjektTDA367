package com.Pubrunda.entities.post.dto.request;

import com.Pubrunda.entities.user.User;
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
    private User author;
    private LocalDateTime after;
    private LocalDateTime before;

}
