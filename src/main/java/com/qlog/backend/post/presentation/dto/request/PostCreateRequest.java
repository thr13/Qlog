package com.qlog.backend.post.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class PostCreateRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotEmpty
    private List<Long> categoryIds;
}
