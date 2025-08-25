package com.qlog.backend.post.domain.repository;

import com.qlog.backend.post.domain.model.Post;
import com.qlog.backend.post.presentation.dto.request.PostSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostQueryRepository {

    Page<Post> searchPosts(PostSearchRequest req, Pageable pageable);

}
