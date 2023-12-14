package com.Pubrunda;

import com.Pubrunda.entities.user.User;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthorizationManager {

    public static boolean hasAuthorityOfUser(User authenticatedUser, User user) {
        return authenticatedUser.getId() == user.getId();
    }
}
