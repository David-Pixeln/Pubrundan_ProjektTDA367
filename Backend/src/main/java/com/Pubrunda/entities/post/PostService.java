package com.Pubrunda.entities.post;

import com.Pubrunda.entities.post.dto.request.PostQueryParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post getPostById(long postId) {
        return postRepository.findById(postId).orElseThrow();
    }

    public List<Post> getAllPosts(PostQueryParams params) {

        PostSpecifications postSpecifications = new PostSpecifications(params);
        return postRepository.findAll(postSpecifications);
    }

    public Post createPost(Post newPost) {
        return postRepository.save(newPost);
    }

    public void deletePost(long postId) {
        postRepository.deleteById(postId);
    }
}
