package io.poststead.poststeadpostservice.repository;

import io.poststead.poststeadpostservice.model.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, UUID> {

    List<PostEntity> getPostsByCreatedBy(String createdBy);

    Long countByCreatedBy(String createdBy);

}
