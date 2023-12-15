package com.Pubrunda.entities.profile;

import com.Pubrunda.AuthorizationManager;
import com.Pubrunda.entities.profile.dto.request.UpdateProfileParams;
import com.Pubrunda.events.CreationEvent;
import com.Pubrunda.entities.profile.dto.request.ProfileQueryParams;
import com.Pubrunda.entities.user.User;
import com.Pubrunda.exception.AuthorizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    public Profile getProfile(long id) {
        return profileRepository.findById(id).orElseThrow();
    }

    public Profile getProfile(User user) {
        return getProfile(user.getId());
    }

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public List<Profile> getAllProfiles(ProfileQueryParams params) {
        return profileRepository.findAll(new ProfileSpecifications(params));
    }

    private Profile createProfileForUser(User user) {
        Profile profile = Profile.builder()
                .user(user)
                .build();

        return profileRepository.save(profile);
    }

    public Profile updateProfile(User authenticatedUser, long profileId, UpdateProfileParams newProfileDetails) {
        return updateProfile(authenticatedUser, getProfile(profileId), newProfileDetails);
    }

    public Profile updateProfile(User authenticatedUser, Profile profile, UpdateProfileParams newProfileDetails) {
        if (!AuthorizationManager.hasAuthorityOfUser(authenticatedUser, profile.getUser())) {
            throw new AuthorizationException("You are not allowed to update this profile");
        }

        return updateProfile(profile, newProfileDetails);
    }

    private Profile updateProfile(Profile profile, UpdateProfileParams newProfileDetails) {
        if (newProfileDetails.getName() != null) {
            profile.setName(newProfileDetails.getName());
        }

        if (newProfileDetails.getDescription() != null) {
            profile.setDescription(newProfileDetails.getDescription());
        }

        return profileRepository.save(profile);
    }

    @EventListener
    public void handleProfileCreation(CreationEvent<User> event) {
        createProfileForUser(event.getCreatedObject());
    }

}
