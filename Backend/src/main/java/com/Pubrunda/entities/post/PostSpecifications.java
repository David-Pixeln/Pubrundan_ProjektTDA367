package com.Pubrunda.entities.post;

import com.Pubrunda.EntitySpecifications;
import com.Pubrunda.entities.post.dto.PostQueryParams;
import lombok.RequiredArgsConstructor;
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

}
