package com.Pubrunda.entities.profile.dto.response;

import com.Pubrunda.entities.user.dto.response.UserDTO;
import lombok.Data;

@Data
public class ProfileDTO {

    private String name;
    private String description;
    private UserDTO user;

}
