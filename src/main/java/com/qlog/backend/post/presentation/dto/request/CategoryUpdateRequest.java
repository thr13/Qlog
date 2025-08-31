package com.qlog.backend.post.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CategoryUpdateRequest {

    @NotBlank
    private String name;

}
