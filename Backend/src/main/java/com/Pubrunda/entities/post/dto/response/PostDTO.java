package com.Pubrunda.entities.post.dto.response;

import com.Pubrunda.entities.user.dto.response.UserDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDTO {

    private long id;
    private UserDTO author;
    private LocalDateTime createdAt;
    private String imagePath;
    private String content;

}
