package com.Pubrunda.entities.post.dto;

import com.Pubrunda.entities.user.User;
import com.Pubrunda.entities.user.dto.UserDTO;
import lombok.Data;

@Data
public class PostDTO {
    private long id;
    private UserDTO author;

}
