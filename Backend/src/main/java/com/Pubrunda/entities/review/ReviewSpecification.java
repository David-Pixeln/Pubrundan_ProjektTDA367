package com.Pubrunda.entities.review;

import com.Pubrunda.EntitySpecifications;
import com.Pubrunda.entities.review.DTO.request.ReviewQueryParams;
import com.Pubrunda.specifications.Specifications;
import com.Pubrunda.specifications.TimeSpecifications;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;


@RequiredArgsConstructor
public class ReviewSpecification extends EntitySpecifications<Review> {

    private final ReviewQueryParams reviewParams;

    @Override
    protected Collection<Specification<Review>> getSpecifications(Root<Review> root) {
        return List.of(
                Specifications.byId(reviewParams.getAuthorId(), root.get("author").get("id")),
                Specifications.byString(reviewParams.getAuthorUsername(), root.get("author").get("username")),
                TimeSpecifications.byTimeAfter(reviewParams.getAfter(), root.get("createdAt")),
                TimeSpecifications.byTimeBefore(reviewParams.getBefore(), root.get("createdAt"))
        );
    }

}
