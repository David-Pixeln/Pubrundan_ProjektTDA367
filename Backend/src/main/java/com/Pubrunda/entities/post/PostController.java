package com.Pubrunda.entities.post;

import com.Pubrunda.DTOMapper;
import com.Pubrunda.dto.response.MessageResponse;
import com.Pubrunda.entities.image.Image;
import com.Pubrunda.entities.image.ImageService;
import com.Pubrunda.entities.image.dto.response.ImageDTO;
import com.Pubrunda.entities.post.dto.request.CreatePostDTO;
import com.Pubrunda.entities.post.dto.request.PostQueryParams;
import com.Pubrunda.entities.post.dto.response.PostDTO;
import com.Pubrunda.entities.user.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;
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
    public PostDTO createPost(@AuthenticationPrincipal User authenticatedUser, CreatePostDTO newPost) {
        return DTOMapper.convertToDto(postService.createPost(authenticatedUser, newPost), PostDTO.class);
    }

    // DELETE
    @DeleteMapping("/{postId}")
    public MessageResponse deletePost(@AuthenticationPrincipal User authenticatedUser, @PathVariable long postId) {
        postService.deletePost(authenticatedUser, postId);
        return new MessageResponse("Post Deleted Successfully");
    }

}
