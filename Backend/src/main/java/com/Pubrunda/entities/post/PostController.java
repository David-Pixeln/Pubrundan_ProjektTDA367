package com.Pubrunda.entities.post;

import com.Pubrunda.DTOMapper;
import com.Pubrunda.dto.response.MessageResponse;
import com.Pubrunda.entities.post.dto.request.CreatePostDTO;
import com.Pubrunda.entities.post.dto.request.PostQueryParams;
import com.Pubrunda.entities.post.dto.response.PostDTO;
import com.Pubrunda.entities.user.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.baseurl}/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // READ
    @GetMapping("/{postId}")
    public PostDTO getPostById(@PathVariable long postId) {
        return DTOMapper.convertToDto(postService.getPostById(postId), PostDTO.class);
    }

    @GetMapping
    public List<PostDTO> getAllPosts(PostQueryParams params) {
        return DTOMapper.convertToDto(postService.getAllPosts(params), PostDTO.class);
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDTO createPost(@AuthenticationPrincipal User authenticatedUser, @RequestBody CreatePostDTO newPost) {
        return DTOMapper.convertToDto(postService.createPost(authenticatedUser, newPost), PostDTO.class);
    }

    // DELETE
    @DeleteMapping("/{postId}")
    public MessageResponse deletePost(@AuthenticationPrincipal User authenticatedUser, @PathVariable long postId) {
        postService.deletePost(authenticatedUser, postId);
        return new MessageResponse("Post Deleted Successfully");
    }

}
