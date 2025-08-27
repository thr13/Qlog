package com.qlog.backend.post.presentation;

import com.qlog.backend.post.application.CategoryService;
import com.qlog.backend.post.application.PostService;
import com.qlog.backend.post.presentation.dto.request.CategoryCreateRequest;
import com.qlog.backend.post.presentation.dto.response.CategoryResponse;
import com.qlog.backend.post.presentation.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final PostService postService;

    /**
     * 카테고리 생성 API
     * @param req 카테고리명
     * @return 201 CREATED, PostResponse(카테고리 ID, 카테고리명)
     */
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryCreateRequest req) {
        CategoryResponse response = categoryService.createCategory(req);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 카테고리 목록 조회 API
     * @return 200 OK, PostResponse(카테고리 ID, 카테고리명) 목록
     */
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> response = categoryService.getAllCategories();

        return ResponseEntity.ok(response);
    }

    /**
     * 카테고리에 소속된 게시글 목록 조회 API
     * @param categoryId 카테고리 식별키
     * @param pageable 페이지블 정보
     * @return 200 OK, 페이징 처리된 PostResponse
     */
    @GetMapping("/{categoryId}/posts")
    public ResponseEntity<Page<PostResponse>> getPostsByCategory(
            @PathVariable Long categoryId,
            @PageableDefault(sort = "createdAt") Pageable pageable) {
        Page<PostResponse> response = postService.getPostListByCategory(categoryId, pageable);

        return ResponseEntity.ok(response);
    }

}
