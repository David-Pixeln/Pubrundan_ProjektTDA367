package com.Pubrunda.entities.user;

import com.Pubrunda.EntitySpecifications;
import com.Pubrunda.entities.user.dto.request.UserQueryParams;
import com.Pubrunda.specifications.Specifications;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class UserSpecifications extends EntitySpecifications<User> {

    private final UserQueryParams userParams;

    @Override
    protected Collection<Specification<User>> getSpecifications(Root<User> root) {
        return List.of(
                Specifications.byString(userParams.getUsername(), root.get("username"))
        );
    }

}
