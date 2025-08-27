package com.qlog.backend.post.application;

import com.qlog.backend.post.domain.model.Category;
import com.qlog.backend.post.domain.repository.CategoryRepository;
import com.qlog.backend.post.exception.DuplicateCategoryException;
import com.qlog.backend.post.presentation.dto.request.CategoryCreateRequest;
import com.qlog.backend.post.presentation.dto.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    //카테고리 조회
    @Transactional
    public CategoryResponse createCategory(CategoryCreateRequest req) {
        checkCategory(req.getName());
        Category category = new Category(req.getName());
        categoryRepository.save(category);

        return CategoryResponse.from(category);
    }

    //카테고리 목록 조회
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toList());
    }

    private void checkCategory(String name) {
        categoryRepository.findByName(name).ifPresent(c -> {
            throw new DuplicateCategoryException("이미 존재하는 카테고리입니다.");
        });
    }
}
