package com.Pubrunda.entities.post;

import com.Pubrunda.AuthorizationManager;
import com.Pubrunda.entities.post.dto.request.CreatePostDTO;
import com.Pubrunda.entities.post.dto.request.PostQueryParams;
import com.Pubrunda.entities.post.dto.response.PostDTO;
import com.Pubrunda.entities.user.User;
import com.Pubrunda.exception.AuthorizationException;
import com.Pubrunda.exception.MissingRequiredAttributeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post getPostById(long postId) {
        return postRepository.findById(postId).orElseThrow();
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> getAllPosts(PostQueryParams params) {
        PostSpecifications postSpecifications = new PostSpecifications(params);
        return postRepository.findAll(postSpecifications);
    }

    public Post createPost(User authenticatedUser, CreatePostDTO newPost) {
        try {
            Post post = Post.builder()
                    .author(authenticatedUser)
                    .content(newPost.getContent())
                    .imagePath(newPost.getImagePath())
                    .createdAt(LocalDateTime.now())
                    .build();

            return postRepository.save(post);
        } catch(NullPointerException e) {
            throw new MissingRequiredAttributeException();
        }
    }

    public void deletePost(User authenticatedUser, long postId) {
        User existingUser = postRepository.findById(postId).orElseThrow().getAuthor();

        if (!AuthorizationManager.hasAuthorityOfUser(authenticatedUser, existingUser)) {
            throw new AuthorizationException("You are not allowed to delete this post");
        }

        postRepository.deleteById(postId);
    }

}
