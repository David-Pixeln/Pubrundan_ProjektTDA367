package com.Pubrunda.entities.friendList.dto.response;

import com.Pubrunda.entities.user.dto.response.UserDTO;
import lombok.Data;

import java.util.List;

@Data
public class FriendListDTO {

    private long id;
    private UserDTO owner;
    private List<UserDTO> friends;

}
