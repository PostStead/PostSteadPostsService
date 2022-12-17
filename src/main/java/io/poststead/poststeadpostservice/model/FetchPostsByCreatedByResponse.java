package io.poststead.poststeadpostservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FetchPostsByCreatedByResponse {

    private List<Post> data;

    private Pagination pagination;

}
