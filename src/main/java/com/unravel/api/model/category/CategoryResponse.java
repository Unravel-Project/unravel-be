package com.unravel.api.model.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CategoryResponse {

    private Long id;

    private String name;

    private String slug;

    private String description;

}
