package com.Pubrunda.entities.post;

import com.Pubrunda.entities.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private long id;

    @NonNull
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @NonNull
    private LocalDateTime createdAt;

    @NonNull
    private String imagePath;

    private int numberOfLikes; // FIXME: Should be database table?

    private String content;

}