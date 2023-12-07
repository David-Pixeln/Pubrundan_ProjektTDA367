package com.Pubrunda.entities.review.DTO.response;

import com.Pubrunda.entities.user.dto.response.UserDTO;
import lombok.Data;

@Data
public class ReviewDTO {

    private long id;
    private UserDTO author;

}
