package com.Pubrunda.entities.review;

import com.Pubrunda.EntitySpecifications;
import com.Pubrunda.entities.review.DTO.request.ReviewQueryParams;
import com.Pubrunda.entities.user.User;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class ReviewSpecification extends EntitySpecifications<Review> {

    private final ReviewQueryParams reviewParams;

    @Override
    protected Collection<Specification<Review>> getSpecifications() {
        return Stream.of(byAuthor(reviewParams.getAuthor()), byTimeAfter(reviewParams.getCreatedAt()), byTimeBefore(reviewParams.getCreatedAt())).toList();
    }

    public Specification<Review> byAuthor(User author) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (author != null) {
                predicate = builder.and(predicate, builder.equal(root.get("author"), author));
            }
            return predicate;
        };
    }

    public Specification<Review> byTimeBefore(LocalDateTime createdAt) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (createdAt != null) {
                predicate = builder.and(predicate, builder.lessThanOrEqualTo(root.get("createdAt"), createdAt));
            }

            return predicate;
        };
    }

    public Specification<Review> byTimeAfter(LocalDateTime createdAt) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (createdAt != null) {
                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get("createdAt"), createdAt));
            }

            return predicate;
        };
    }
}
