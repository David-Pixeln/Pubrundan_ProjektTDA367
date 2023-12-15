package com.Pubrunda.entities.comment;

import com.Pubrunda.EntitySpecifications;
import com.Pubrunda.entities.comment.dto.request.CommentQueryParams;
import com.Pubrunda.specifications.Specifications;
import com.Pubrunda.specifications.TimeSpecifications;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CommentSpecifications extends EntitySpecifications<Comment> {

    private final CommentQueryParams commentParams;

    @Override
    protected Collection<Specification<Comment>> getSpecifications(Root<Comment> root) {
        return List.of(
                Specifications.byId(commentParams.getAuthorId(), root.get("author").get("id")),
                Specifications.byString(commentParams.getAuthorUsername(), root.get("author").get("username")),
                TimeSpecifications.byTimeBefore(commentParams.getBefore(), root.get("createdAt")),
                TimeSpecifications.byTimeAfter(commentParams.getAfter(), root.get("createdAt"))
        );
    }

}
