package com.qlog.backend.post.presentation.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PostSearchRequest {

    private String search; //검색할 제목(내용)
    private Long categoryId; //카테고리 고유식별키
    private UUID userId; //사용자 고유식별키

}
