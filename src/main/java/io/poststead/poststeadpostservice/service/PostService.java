package io.poststead.poststeadpostservice.service;

import io.poststead.poststeadpostservice.exception.user_exception.PostNotFoundException;
import io.poststead.poststeadpostservice.model.CreatePostRequest;
import io.poststead.poststeadpostservice.model.PostEntity;
import io.poststead.poststeadpostservice.model.dto.PostDto;
import io.poststead.poststeadpostservice.repository.PostRepository;
import io.poststead.poststeadpostservice.utility.PostConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PostService {

    private PostRepository postRepository;

    public PostDto createPost(CreatePostRequest createPostRequest, String username) {
        PostEntity savedPostEntity = postRepository.save(mapToSavePostEntity(createPostRequest, username));
        return mapToPostDto(postRepository.save(savedPostEntity));
    }

    public List<PostDto> fetchPostsByCreatedBy(String username) {
        return postRepository.getPostsByCreatedBy(username)
                .stream()
                .map(this::mapToPostDto)
                .toList();
    }

    public PostDto fetchPostById(UUID postId) {
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        return mapToPostDto(postEntity);
    }

    public List<PostDto> fetchAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(this::mapToPostDto)
                .toList();
    }

    public void deletePostById(UUID id) {
        postRepository.deleteById(id);
    }

    private PostDto mapToPostDto(PostEntity postEntity) {
        return PostDto.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .url(postEntity.getUrl())
                .text(postEntity.getText())
                .createdBy(postEntity.getCreatedBy())
                .build();
    }

    private PostEntity mapToSavePostEntity(CreatePostRequest request, String username) {
        return PostEntity.builder()
                .title(request.getTitle())
                .text(request.getText())
                .url(request.getUrl())
                .createdBy(username)
                .build();
    }

}
