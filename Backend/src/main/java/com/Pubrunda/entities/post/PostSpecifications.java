package com.Pubrunda.entities.post;

import com.Pubrunda.EntitySpecifications;
import com.Pubrunda.entities.post.dto.request.PostQueryParams;
import com.Pubrunda.entities.user.User;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class PostSpecifications extends EntitySpecifications<Post> {

    private final PostQueryParams postParams;


    @Override
    protected Collection<Specification<Post>> getSpecifications() {
        return Stream.of(
                byId(postParams.getId()),
                byAuthorId(postParams.getAuthorId()),
                byAuthorUsername(postParams.getAuthorUsername()),
                byTimeAfter((postParams.getAfter())),
                byTimeBefore(postParams.getBefore())
        ).toList();
    }

    public Specification<Post> byId(Long id) {
        return (root, query, builder) -> id != null ? builder.equal(root.get("id"), id) : null;
    }

    public Specification<Post> byAuthorId(Long id) {
        return (root, query, builder) -> id != null ? builder.equal(root.get("author").get("id"), id) : null;
    }

    public Specification<Post> byAuthorUsername(String username) {
        return (root, query, builder) -> username != null ? builder.equal(root.get("author").get("username"), username) : null;
    }

    public Specification<Post> byTimeBefore(LocalDateTime before) {
        return (root, query, builder) -> before != null ? builder.lessThanOrEqualTo(root.get("createdAt"), before) : null;
    }

    public Specification<Post> byTimeAfter(LocalDateTime after) {
        return (root, query, builder) -> after != null ? builder.greaterThanOrEqualTo(root.get("createdAt"), after) : null;
    }

}

