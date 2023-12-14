package com.Pubrunda.entities.pub.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.Pubrunda.entities.user.User;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PubQueryParams {

    private Long ownerId;
    private String ownerUsername;
    private String name;
    private LocalDateTime OpenBefore;
    private LocalDateTime OpenAfter;
    private LocalDateTime ClosedBefore;

}
