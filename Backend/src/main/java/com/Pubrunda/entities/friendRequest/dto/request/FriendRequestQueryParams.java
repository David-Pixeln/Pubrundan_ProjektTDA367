package com.Pubrunda.entities.friendRequest.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FriendRequestQueryParams {

    private Long id;
    private Long fromId;
    private Long toId;
    private String fromUsername;
    private String toUsername;
    private LocalDateTime after;
    private LocalDateTime before;

}
