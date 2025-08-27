package com.qlog.backend.post.domain.repository;

import com.qlog.backend.post.domain.model.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByPost_Id(Long postId, Pageable pageable);

}
