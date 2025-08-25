package com.qlog.backend.post.domain.repository;

import com.qlog.backend.post.domain.model.PostLike;
import com.qlog.backend.post.domain.model.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {

}
