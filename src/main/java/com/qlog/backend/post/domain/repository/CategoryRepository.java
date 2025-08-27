package com.qlog.backend.post.domain.repository;

import com.qlog.backend.post.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * 카테고리명으로 카테고리 조회
     * @param name 검색할 카테고리 이름
     * @return Optional 로 감싼 Category 객체
     */
    Optional<Category> findByName(String name);

}
