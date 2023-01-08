package io.poststead.poststeadpostservice.service;

import io.poststead.poststeadpostservice.model.PostEntity;
import io.poststead.poststeadpostservice.model.dto.PostDto;
import io.poststead.poststeadpostservice.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PostService {

    private PostRepository postRepository;

    public PostDto createPost(PostDto postDto) {
        PostEntity savedPostEntity = postRepository.save(PostEntity.builder()
                .content(postDto.getContent())
                .createdBy(postDto.getCreatedBy())
                .build());
        return PostDto.builder()
                .id(savedPostEntity.getId())
                .content(savedPostEntity.getContent())
                .createdBy(savedPostEntity.getCreatedBy())
                .build();
    }

    public Page<PostEntity> fetchPosts(String username) {
        Pageable pageRequest = PageRequest.of(
                0,
                25,
                Sort.by(Sort.Direction.ASC, "createdBy"));
        List<PostEntity> postEntityList = postRepository.getPostsByCreatedBy(username);
        Long postsNumber = postRepository.countByCreatedBy(username);
        return new PageImpl<>(postEntityList, pageRequest, postsNumber);
    }

    public PostDto fetchPostById(UUID postId) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow();
        return PostDto.builder()
                .id(postEntity.getId())
                .content(postEntity.getContent())
                .createdBy(postEntity.getCreatedBy())
                .build();
    }

    public Page<PostEntity> fetchAllPosts() {
        Pageable pageRequest = PageRequest.of(
                0,
                25,
                Sort.by(Sort.Direction.ASC, "createdBy"));
        List<PostEntity> postEntityList = postRepository.findAll();
        Long postsNumber = postRepository.count();
        return new PageImpl<>(postEntityList, pageRequest, postsNumber);
    }

}
