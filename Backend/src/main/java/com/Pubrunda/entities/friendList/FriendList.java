package com.Pubrunda.entities.friendList;

import com.Pubrunda.entities.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendList {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private long id;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id")
    @NonNull
    User owner;

    @ManyToMany
    List<User> friends;

}
