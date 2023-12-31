package com.Pubrunda.entities.review.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewDTO {

    private Integer rating;

    private String mediaPath;

    private String content;

}
