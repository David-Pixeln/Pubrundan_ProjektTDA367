package com.Pubrunda.specifications;

import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TimeSpecifications {

    public static <T> Specification<T> byTimeBefore(LocalDateTime before, Path<LocalDateTime> path) {
        return (root, query, builder) -> before != null ? builder.lessThanOrEqualTo(path, before) : null;
    }

    public static <T> Specification<T> byTimeAfter(LocalDateTime after, Path<LocalDateTime> path) {
        return (root, query, builder) -> after != null ? builder.greaterThanOrEqualTo(path, after) : null;
    }

}
