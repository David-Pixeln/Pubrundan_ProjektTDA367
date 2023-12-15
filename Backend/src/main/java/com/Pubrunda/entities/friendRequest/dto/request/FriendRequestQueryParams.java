package com.Pubrunda.entities.friendRequest.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FriendRequestQueryParams {

    private Long id;
    private Long fromId;
    private Long toId;
    private String fromUsername;
    private String toUsername;
    private LocalDateTime after;
    private LocalDateTime before;

}
