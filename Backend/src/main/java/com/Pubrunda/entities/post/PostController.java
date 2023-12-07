package com.Pubrunda.entities.post;

import com.Pubrunda.dto.response.MessageResponse;
import com.Pubrunda.entities.post.dto.response.PostDTO;
import com.Pubrunda.entities.post.dto.request.PostQueryParams;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
        return postService.getById(postId);
    }

    // CREATE
    @PostMapping()
    public Post createPost(@RequestBody Post newPost) {
        return postService.createPost(newPost);
    }

    // DELETE
    @DeleteMapping("/{postId}")
    public MessageResponse deletePost(@PathVariable long postId) {
        postService.deletePost(postId);
        return new MessageResponse("Post Deleted Successfully");
    }

    @GetMapping("/{postId}")
    public List<PostDTO> getAllPosts(PostQueryParams params) {
        return postService.getAll(params).stream().map(post -> modelMapper.map(post, PostDTO.class)).toList();
    }
}
