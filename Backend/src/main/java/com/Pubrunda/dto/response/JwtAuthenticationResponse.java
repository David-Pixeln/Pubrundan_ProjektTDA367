package com.Pubrunda.dto.response;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {

    private String jwt;

}