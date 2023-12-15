package com.Pubrunda.entities.friendRequest.dto.response;

import com.Pubrunda.entities.user.dto.response.UserDTO;
import lombok.Data;

@Data
public class FriendRequestDTO {

    private long id;
    private UserDTO from;
    private UserDTO to;

}
