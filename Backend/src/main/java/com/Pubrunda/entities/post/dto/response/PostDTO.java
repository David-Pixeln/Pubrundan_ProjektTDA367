package com.Pubrunda.entities.post.dto.response;

import com.Pubrunda.entities.user.dto.UserDTO;
import lombok.Data;

@Data
public class PostDTO {
    private long id;
    private UserDTO author;

}
