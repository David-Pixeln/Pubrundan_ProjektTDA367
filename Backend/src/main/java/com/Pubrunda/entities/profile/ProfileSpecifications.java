package com.Pubrunda.entities.profile;

import com.Pubrunda.EntitySpecifications;
import com.Pubrunda.entities.profile.dto.request.ProfileQueryParams;
import com.Pubrunda.specifications.Specifications;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class ProfileSpecifications extends EntitySpecifications<Profile> {

    private final ProfileQueryParams profileParams;

    @Override
    protected Collection<Specification<Profile>> getSpecifications(Root<Profile> root) {
        return List.of(
                Specifications.byString(profileParams.getUsername(), root.get("user").get("username"))
        );
    }

}
