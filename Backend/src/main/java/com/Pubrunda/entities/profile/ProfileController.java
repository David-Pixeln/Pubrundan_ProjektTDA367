package com.Pubrunda.entities.profile;

import com.Pubrunda.DTOMapper;
import com.Pubrunda.entities.profile.dto.request.ProfileQueryParams;
import com.Pubrunda.entities.profile.dto.request.UpdateProfileParams;
import com.Pubrunda.entities.profile.dto.response.ProfileDTO;
import com.Pubrunda.entities.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.baseurl}/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;


    @GetMapping("/{profileId}")
    public ProfileDTO getProfileById(@PathVariable long profileId) {
        return DTOMapper.convertToDto(profileService.getProfile(profileId), ProfileDTO.class);
    }

    @GetMapping
    public List<ProfileDTO> getAllProfiles(ProfileQueryParams params) {
        return DTOMapper.convertToDto(profileService.getAllProfiles(params), ProfileDTO.class);
    }

    @PutMapping("/{profileId}")
    public ProfileDTO updateProfile(@AuthenticationPrincipal User authenticatedUser,
                                    @PathVariable long profileId,
                                    @RequestBody UpdateProfileParams newProfileDetails) {
        return DTOMapper.convertToDto(profileService.updateProfile(authenticatedUser, profileId, newProfileDetails), ProfileDTO.class);
    }

}
