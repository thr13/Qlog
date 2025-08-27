package com.qlog.backend.post.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostUpdateRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
