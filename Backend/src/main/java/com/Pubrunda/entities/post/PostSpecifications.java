package com.Pubrunda.entities.post;

import com.Pubrunda.EntitySpecifications;
import com.Pubrunda.entities.post.dto.request.PostQueryParams;
import com.Pubrunda.specifications.Specifications;
import com.Pubrunda.specifications.TimeSpecifications;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;


@RequiredArgsConstructor
public class PostSpecifications extends EntitySpecifications<Post> {

    private final PostQueryParams postParams;

    @Override
    protected Collection<Specification<Post>> getSpecifications(Root<Post> root) {
        return List.of(
                Specifications.byId(postParams.getAuthorId(), root.get("author").get("id")),
                Specifications.byString(postParams.getAuthorUsername(), root.get("author").get("username")),
                TimeSpecifications.byTimeBefore(postParams.getBefore(), root.get("createdAt")),
                TimeSpecifications.byTimeAfter(postParams.getAfter(), root.get("createdAt"))
        );
    }

}