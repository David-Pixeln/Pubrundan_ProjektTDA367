package com.Pubrunda.entities.post;

import com.Pubrunda.EntitySpecifications;
import com.Pubrunda.entities.post.dto.PostQueryParams;
import com.Pubrunda.entities.user.User;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class PostSpecifications extends EntitySpecifications<Post> {

    private final PostQueryParams postParams;

    @Override
    protected Collection<Specification<Post>> getSpecifications() {
        return Stream.of(byAuthor(postParams.getAuthor())).toList();
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
}

