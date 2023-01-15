package io.poststead.poststeadpostservice.controller;

import io.poststead.poststeadpostservice.model.CreatePostRequest;
import io.poststead.poststeadpostservice.model.FetchPostsByCreatedByResponse;
import io.poststead.poststeadpostservice.model.dto.PostDto;
import io.poststead.poststeadpostservice.service.PostService;
import io.poststead.poststeadpostservice.utility.PostConstants;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/posts")
@AllArgsConstructor
@CrossOrigin(origins = "https://poststead.online")
public class PostController {

    private final RabbitTemplate rabbitTemplate;
    private PostService postService;

    @PreAuthorize("principal.username == #username")
    @PostMapping(value = "/{username}")
    ResponseEntity<PostDto> createPost(@PathVariable String username, @RequestBody CreatePostRequest requestBody) {
        PostDto savedPost = postService.createPost(requestBody, username);
        ResponseEntity<PostDto> response = ResponseEntity.created(
                URI.create(PostConstants.CREATE_POST_ROUTE + username + "/" + savedPost.getId()))
            .body(savedPost);
        sendRabbitMessage(response.toString());
        return response;
    }

    @GetMapping(value = "/{username}")
    ResponseEntity<FetchPostsByCreatedByResponse> getAllPostsByUsername(@PathVariable String username) {
        List<PostDto> postEntityList = postService.fetchPostsByCreatedBy(username);
        ResponseEntity<FetchPostsByCreatedByResponse> response = ResponseEntity.ok(
            FetchPostsByCreatedByResponse.builder()
                .data(postEntityList)
                .build()
        );
        sendRabbitMessage(response.toString());
        return response;
    }

    @GetMapping(value = "post/{postId}")
    ResponseEntity<PostDto> getPostById(@PathVariable UUID postId) {
        ResponseEntity<PostDto> response = ResponseEntity.ok(postService.fetchPostById(postId));
        sendRabbitMessage(response.toString());
        return response;
    }

    @GetMapping(value = "/getAll")
    ResponseEntity<FetchPostsByCreatedByResponse> getAllPosts() {
        List<PostDto> postEntityList = postService.fetchAllPosts();
        ResponseEntity<FetchPostsByCreatedByResponse> response = ResponseEntity.ok(
            FetchPostsByCreatedByResponse.builder()
                .data(postEntityList)
                .build()
        );
        sendRabbitMessage(response.toString());
        return response;
    }

    @PreAuthorize("principal.username == #username")
    @DeleteMapping(value = "/delete/{username}/{postId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username, @PathVariable UUID postId) {
        postService.deletePostById(postId);
        ResponseEntity<Void> result = ResponseEntity.noContent().build();
        sendRabbitMessage(result.toString());
        return result;
    }

    private void sendRabbitMessage(String event) {
        rabbitTemplate.convertAndSend("io.poststead.exchange", "io.poststead.audit", event);
    }

}
