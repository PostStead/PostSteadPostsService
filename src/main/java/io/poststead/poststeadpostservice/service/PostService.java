package io.poststead.poststeadpostservice.service;

import io.poststead.poststeadpostservice.model.Post;
import io.poststead.poststeadpostservice.model.dto.PostDto;
import io.poststead.poststeadpostservice.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    private PostRepository postRepository;

    public PostDto createPost(PostDto postDto) {
        Post savedPost = postRepository.save(Post.builder()
                        .content(postDto.getContent())
                        .createdBy(postDto.getCreatedBy())
                .build());
        return PostDto.builder()
                .id(savedPost.getId())
                .content(savedPost.getContent())
                .createdBy(savedPost.getCreatedBy())
                .build();
    }

    public Page<Post> fetchPosts(String username) {
        Pageable pageRequest = PageRequest.of(
                0,
                25,
                Sort.by(Sort.Direction.ASC, "createdBy")
        );
        List<Post> postList = postRepository.getPostsByCreatedBy(username);
        Long postsNumber = postRepository.countByCreatedBy(username);
        return new PageImpl<>(postList, pageRequest, postsNumber);
    }

}
