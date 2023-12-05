package com.Pubrunda.entities.friendRequest;

import com.Pubrunda.entities.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private long id;

    @NonNull
    private LocalDateTime createdAt;

    @NonNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id")
    private User from;

    @NonNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id")
    private User to;

}
