package com.Pubrunda.entities.pub;

import com.Pubrunda.entities.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Pub {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private long id;

    @NonNull
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User owner;

    @NonNull
    private String name;

    private double longitude;

    private double latitude;

    @NonNull
    private LocalDateTime openingTime;

    @NonNull
    private LocalDateTime closingTime;

    @NonNull
    private LocalDateTime lastUpdatedTime;

}
