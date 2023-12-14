package com.Pubrunda;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

public abstract class EntitySpecifications<T> implements Specification<T> {

    private Root<T> root;

    protected abstract Collection<Specification<T>> getSpecifications(Root<T> root);

    @Override
    public Predicate toPredicate(@NonNull Root<T> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        this.root = root;
        return getCompositeSpecification().toPredicate(root, query, criteriaBuilder);
    }

    private Specification<T> getCompositeSpecification() {
        return combine(getSpecifications(root));
    }

    // PARENT
    private Specification<T> combine(Collection<Specification<T>> specifications) {
        Specification<T> combinedSpecification = Specification.where(null);

        for (Specification<T> specification : specifications) {
            combinedSpecification = combinedSpecification.and(specification);
        }

        return combinedSpecification;
    }

}
