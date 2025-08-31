package com.qlog.backend.post.application;

import com.qlog.backend.post.domain.model.Category;
import com.qlog.backend.post.domain.repository.CategoryRepository;
import com.qlog.backend.post.exception.DuplicateCategoryException;
import com.qlog.backend.post.exception.NotFoundCategoryException;
import com.qlog.backend.post.presentation.dto.request.CategoryCreateRequest;
import com.qlog.backend.post.presentation.dto.request.CategoryUpdateRequest;
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

    //카테고리 생성
    @Transactional
    public CategoryResponse createCategory(CategoryCreateRequest req) {
        if (categoryRepository.findByName(req.getName()).isPresent()) {
            throw new DuplicateCategoryException("이미 존재하는 카테고리 입니다.");
        }

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

    //카테고리 수정
    @Transactional
    public void updateCategory(Long categoryId, CategoryUpdateRequest request) {
        Category category = findCategoryById(categoryId);

        categoryRepository.findByName(request.getName()).ifPresent(existingCategory -> {
            if (!existingCategory.getId().equals(categoryId)) {
                throw new DuplicateCategoryException("이미 사용 중인 카테고리 이름입니다.");
            }
        });

        category.changeName(request.getName());
    }

    //카테고리 삭제
    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = findCategoryById(categoryId);

        if (!category.getPosts().isEmpty()) {
            throw new DuplicateCategoryException("해당 카테고리에 게시글이 존재하여 삭제할 수 없습니다.");
        }

        categoryRepository.delete(category);
    }

    private Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundCategoryException(categoryId + " 해당 카테고리를 찾을 수 없습니다.")
        );
    }

}
