package com.Pubrunda.entities.post.dto;

import com.Pubrunda.entities.user.User;
import lombok.Data;

@Data
public class PostQueryParams {

    private User author;
}
