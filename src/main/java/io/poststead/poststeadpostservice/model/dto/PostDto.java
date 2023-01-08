package io.poststead.poststeadpostservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private UUID id;

    private String text;

    private String title;

    private String url;

    private String createdBy;

}
