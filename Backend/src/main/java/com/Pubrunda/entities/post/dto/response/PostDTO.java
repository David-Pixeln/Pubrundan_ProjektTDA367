package com.Pubrunda.entities.post.dto.response;

import com.Pubrunda.entities.image.dto.response.ImageDTO;
import com.Pubrunda.entities.user.dto.response.UserDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDTO {

    private long id;
    private UserDTO author;
    private LocalDateTime createdAt;
    private List<ImageDTO> images;
    private String content;

}
