package com.Pubrunda.entities.pub;

import com.Pubrunda.EntitySpecifications;
import com.Pubrunda.entities.pub.dto.request.PubQueryParams;
import com.Pubrunda.specifications.Specifications;
import com.Pubrunda.specifications.TimeSpecifications;
import com.fasterxml.jackson.databind.introspect.AnnotatedAndMetadata;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class PubSpecifications extends EntitySpecifications<Pub> {

    private final PubQueryParams pubParams;

    @Override
    protected Collection<Specification<Pub>> getSpecifications(Root<Pub> root) {
        return List.of(
                Specifications.byId(pubParams.getOwnerId(), root.get("owner").get("id")),
                Specifications.byString(pubParams.getOwnerUsername(), root.get("owner").get("username")),
                Specifications.byString(pubParams.getName(), root.get("name")),
                TimeSpecifications.byTimeBefore(pubParams.getOpenBefore(), root.get("openBefore")),
                TimeSpecifications.byTimeAfter(pubParams.getOpenAfter(), root.get("openAfter")),
                TimeSpecifications.byTimeBefore(pubParams.getClosedBefore(), root.get("closedBefore")),
                TimeSpecifications.byTimeAfter(pubParams.getOpenAfter(), root.get("closedAfter"))
        );
    }
}
