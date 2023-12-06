package com.Pubrunda.entities.post.dto;

import com.Pubrunda.entities.user.User;
import lombok.Data;

@Data
public class PostDTO {
    private long id;
    private User author;
    
}
