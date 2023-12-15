package com.Pubrunda.entities.profile;

import com.Pubrunda.entities.image.Image;
import com.Pubrunda.entities.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    @Setter(AccessLevel.NONE)
    private long id;

    private String name;

    private String description;

    @OneToOne
    @JoinColumn(name = "profile_image_id")
    private Image profileImage;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id")
    @NonNull
    private User user;


}
