package com.Pubrunda.entities.friendRequest.dto.request;

import com.Pubrunda.entities.user.dto.response.UserDTO;
import lombok.Data;

@Data
public class CreateFriendRequestDTO {

    private UserDTO to;

}
