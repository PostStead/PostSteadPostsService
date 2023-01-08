package io.poststead.poststeadpostservice.controller;

import io.poststead.poststeadpostservice.model.FetchPostsByCreatedByResponse;
import io.poststead.poststeadpostservice.model.Pagination;
import io.poststead.poststeadpostservice.model.PostEntity;
import io.poststead.poststeadpostservice.model.dto.PostDto;
import io.poststead.poststeadpostservice.service.PostService;
import io.poststead.poststeadpostservice.utility.PostConstants;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("api/posts")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {

    private PostService postService;

    @PreAuthorize("principal.username == #username")
    @PostMapping(value = "/{username}")
    ResponseEntity<PostDto> createPost(
            @PathVariable String username,
            @RequestBody PostDto postDto) {
        PostDto savedPost = postService.createPost(postDto);
        return ResponseEntity.created(URI.create(
                PostConstants.GET_POST_ROUTE + username + "/" + savedPost.getId()))
                .body(savedPost);
    }

    @GetMapping(value = "/{username}")
    ResponseEntity<FetchPostsByCreatedByResponse> fetchUserPosts(@PathVariable String username) {
        Page<PostEntity> postPage = postService.fetchPosts(username);
        return ResponseEntity.ok(FetchPostsByCreatedByResponse.builder()
                .data(postPage.stream().toList())
                .pagination(Pagination.builder()
                        .limit(postPage.getSize())
                        .page(postPage.getNumber())
                        .totalItems(postPage.getTotalElements())
                        .hasNext(postPage.hasNext())
                        .build())
                .build());
    }

    @GetMapping(value = "post/{postId}")
    ResponseEntity<PostDto> fetchPostById(@PathVariable UUID postId) {
        return ResponseEntity.ok(postService.fetchPostById(postId));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/getAll")
    ResponseEntity<FetchPostsByCreatedByResponse> fetchAllPosts() {
        Page<PostEntity> postPage = postService.fetchAllPosts();
        return ResponseEntity.ok(FetchPostsByCreatedByResponse.builder()
                .data(postPage.stream().toList())
                .pagination(Pagination.builder()
                        .limit(postPage.getSize())
                        .page(postPage.getNumber())
                        .totalItems(postPage.getTotalElements())
                        .hasNext(postPage.hasNext())
                        .build())
                .build());
    }

}
