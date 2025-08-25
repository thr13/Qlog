package com.qlog.backend.post.domain.repository;

import com.qlog.backend.post.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
