package com.Pubrunda.entities.comment.dto.response;

import com.Pubrunda.entities.user.dto.response.UserDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {

    private long id;
    private UserDTO author;
    private LocalDateTime createdAt;
    private String content;

}
