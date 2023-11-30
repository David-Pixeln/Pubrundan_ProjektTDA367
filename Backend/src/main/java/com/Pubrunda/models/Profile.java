package com.Pubrunda.models;

import jakarta.persistence.*;
import lombok.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Profile {

    @Id
    @Setter(AccessLevel.NONE)
    private long id;

    private String name;

    private String description;

    private String profileImagePath;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id")
    @NonNull
    private User user;

    public BufferedImage getProfileImage() throws IOException {
        return ImageIO.read(new File(profileImagePath));
    }

}
