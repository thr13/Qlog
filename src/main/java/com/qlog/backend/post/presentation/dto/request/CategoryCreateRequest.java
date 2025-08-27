package com.qlog.backend.post.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CategoryCreateRequest {

    @NotBlank
    private String name;
}
