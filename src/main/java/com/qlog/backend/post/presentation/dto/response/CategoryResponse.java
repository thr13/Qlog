package com.qlog.backend.post.presentation.dto.response;

import com.qlog.backend.post.domain.model.Category;
import lombok.Getter;

@Getter
public class CategoryResponse {

    private final Long categoryId;
    private final String name;

    private CategoryResponse(Category category) {
        this.categoryId = category.getId();
        this.name = category.getName();
    }

    public static CategoryResponse from(Category category) {
        return new CategoryResponse(category);
    }
}
