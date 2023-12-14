package com.Pubrunda.entities.friendRequest;

import com.Pubrunda.EntitySpecifications;
import com.Pubrunda.entities.friendRequest.dto.request.FriendRequestQueryParams;
import com.Pubrunda.specifications.Specifications;
import com.Pubrunda.specifications.TimeSpecifications;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class FriendRequestSpecifications extends EntitySpecifications<FriendRequest> {

    private final FriendRequestQueryParams queryParams;

    @Override
    protected Collection<Specification<FriendRequest>> getSpecifications(Root<FriendRequest> root) {
        return List.of(
                Specifications.byId(queryParams.getFromId(), root.get("from").get("id")),
                Specifications.byId(queryParams.getToId(), root.get("to").get("id")),
                Specifications.byString(queryParams.getFromUsername(), root.get("from").get("username")),
                Specifications.byString(queryParams.getToUsername(), root.get("to").get("username")),
                TimeSpecifications.byTimeBefore(queryParams.getBefore(), root.get("createdAt")),
                TimeSpecifications.byTimeAfter(queryParams.getAfter(), root.get("createdAt"))
        );
    }

}