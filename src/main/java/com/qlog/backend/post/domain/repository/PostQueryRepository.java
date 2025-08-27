package com.qlog.backend.post.domain.repository;

import com.qlog.backend.post.domain.model.Post;
import com.qlog.backend.post.presentation.dto.request.PostSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostQueryRepository {

    /**
     * 게시글 검색
     * @param req 검색 요청
     * @param pageable 페이지 정보
     * @return Page 로 감싼 Post 객체
     */
    Page<Post> searchPosts(PostSearchRequest req, Pageable pageable);

}
