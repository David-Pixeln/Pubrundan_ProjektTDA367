package com.Pubrunda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class MessageResponse {

    private String message;

}
