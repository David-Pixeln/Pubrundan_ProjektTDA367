package com.Pubrunda;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

public abstract class EntitySpecifications<T> implements Specification<T> {

    protected abstract Collection<Specification<T>> getSpecifications();

    @Override
    public Predicate toPredicate(@NonNull Root<T> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        return getCompositeSpecification().toPredicate(root, query, criteriaBuilder);
    }

    private Specification<T> getCompositeSpecification() {
        return combine(getSpecifications());
    }

    // PARENT
    private <T> Specification<T> combine(Collection<Specification<T>> specifications) {
        Specification<T> combinedSpecification = Specification.where(null);

        for (Specification<T> specification : specifications) {
            combinedSpecification = combinedSpecification.and(specification);
        }

        return combinedSpecification;
    }

}
