package com.qlog.backend.post.domain.repository;

import com.qlog.backend.post.domain.model.CommentLike;
import com.qlog.backend.post.domain.model.CommentLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikeId> {

}
