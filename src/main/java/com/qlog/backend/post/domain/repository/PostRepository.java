package com.qlog.backend.post.domain.repository;

import com.qlog.backend.post.domain.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostQueryRepository {

}
