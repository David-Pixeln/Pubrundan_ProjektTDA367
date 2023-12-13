package com.Pubrunda.entities.post;

import com.Pubrunda.dto.response.MessageResponse;
import com.Pubrunda.entities.post.dto.request.PostQueryParams;
import com.Pubrunda.entities.post.dto.response.PostDTO;
import com.Pubrunda.entities.user.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.baseurl}/posts")
@RequiredArgsConstructor
public class PostController {

    private final ModelMapper modelMapper;
    private final PostService postService;

    // READ
    @GetMapping("/{postId}")
    public Post getPostById(@PathVariable long postId) {
        return postService.getPostById(postId);
    }

    @GetMapping
    public List<PostDTO> getAllPosts(PostQueryParams params) {
        return postService.getAllPosts(params).stream().map(post -> modelMapper.map(post, PostDTO.class)).toList();
    }

    // CREATE
    @PostMapping
    public Post createPost(@AuthenticationPrincipal User authenticatedUser, @RequestBody Post newPost) {
        return postService.createPost(authenticatedUser, newPost);
    }

    // DELETE
    @DeleteMapping("/{postId}")
    public MessageResponse deletePost(@AuthenticationPrincipal User authenticatedUser, @PathVariable long postId) {
        postService.deletePost(authenticatedUser, postId);
        return new MessageResponse("Post Deleted Successfully");
    }

}
