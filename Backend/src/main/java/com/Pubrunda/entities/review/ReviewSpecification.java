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
        return Stream.of(
                byAuthorId(reviewParams.getAuthorId()),
                byTimeAfter(reviewParams.getCreatedAt()),
                byTimeBefore(reviewParams.getCreatedAt())
                ).toList();
    }

    public Specification<Review> byAuthorId(Long id) {
        return (root, query, builder) -> id != null ? builder.equal(root.get("author").get("id"), id) : null;
    }

    public Specification<Review> byTimeBefore(LocalDateTime createdAt) {
        return (root, query, builder) -> createdAt != null ? builder.lessThanOrEqualTo(root.get("createdAt"), createdAt) : null;
    }

    public Specification<Review> byTimeAfter(LocalDateTime createdAt) {
        return (root, query, builder) -> createdAt != null ? builder.greaterThanOrEqualTo(root.get("createdAt"), createdAt) : null;
    }

}
