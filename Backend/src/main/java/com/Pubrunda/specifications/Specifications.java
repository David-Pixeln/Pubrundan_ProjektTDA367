package com.Pubrunda.specifications;

import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

public class Specifications {

    public static <T> Specification<T> byId(Long id, Path<Long> path) {
        return (root, query, builder) -> id != null ? builder.equal(path, id) : null;
    }

    public static <T> Specification<T> byString(String string, Path<String> path) {
        return (root, query, builder) -> string != null ? builder.equal(path, string) : null;
    }

}
