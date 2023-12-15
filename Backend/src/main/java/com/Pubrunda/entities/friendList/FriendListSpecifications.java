package com.Pubrunda.entities.friendList;

import com.Pubrunda.EntitySpecifications;
import com.Pubrunda.entities.friendList.dto.request.FriendListQueryParams;
import com.Pubrunda.specifications.Specifications;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class FriendListSpecifications extends EntitySpecifications<FriendList> {

    private final FriendListQueryParams friendListParams;

    @Override
    protected Collection<Specification<FriendList>> getSpecifications(Root<FriendList> root) {
        return List.of(
                Specifications.byString(friendListParams.getUsername(), root.get("owner").get("username"))
        );
    }

}
