package com.Pubrunda.entities.post;

import com.Pubrunda.entities.post.dto.PostQueryParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<Post> findAll(PostQueryParams params) {
        PostSpecifications postSpecifications = new PostSpecifications(params);
        return postRepository.findAll(postSpecifications);
    }
}
