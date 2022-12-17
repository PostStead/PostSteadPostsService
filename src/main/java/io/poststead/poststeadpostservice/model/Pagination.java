package io.poststead.poststeadpostservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {

    private Integer limit;

    private Integer page;

    private Long totalItems;

    private Boolean hasNext;

}
