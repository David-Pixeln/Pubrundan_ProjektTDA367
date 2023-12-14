package com.Pubrunda.entities.review.DTO.response;

import com.Pubrunda.entities.user.dto.response.UserDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDTO {

    private long id;
    private UserDTO author;
    private LocalDateTime createdAt;
    private int rating;
    private String mediaPath;
    private String content;

}
