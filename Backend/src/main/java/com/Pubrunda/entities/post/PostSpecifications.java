package com.Pubrunda.entities.post;

import com.Pubrunda.EntitySpecifications;
import com.Pubrunda.entities.post.dto.request.PostQueryParams;
import com.Pubrunda.entities.user.User;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class PostSpecifications extends EntitySpecifications<Post> {

    private final PostQueryParams postParams;


    @Override
    protected Collection<Specification<Post>> getSpecifications() {
        return Stream.of(
                byAuthor(postParams.getAuthor()),
                byTimeAfter((postParams.getAfter())),
                byTimeBefore(postParams.getBefore())
        ).toList();
    }

    public Specification<Post> byAuthor(User author) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (author != null) {
                predicate = builder.and(predicate, builder.equal(root.get("author"), author));
            }

            return predicate;
        };
    }

    public Specification<Post> byTimeBefore(LocalDateTime before) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (before != null) {
                predicate = builder.and(predicate, builder.lessThanOrEqualTo(root.get("createdAt"), before));
            }

            return predicate;
        };
    }

    public Specification<Post> byTimeAfter(LocalDateTime after) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (after != null) {
                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get("createdAt"), after));
            }

            return predicate;
        };
    }
}

