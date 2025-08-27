package com.qlog.backend.post.presentation;

import com.qlog.backend.post.application.PostService;
import com.qlog.backend.post.presentation.dto.request.PostCreateRequest;
import com.qlog.backend.post.presentation.dto.request.PostUpdateRequest;
import com.qlog.backend.post.presentation.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    /**
     * 게시글 생성 API
     *
     * @param req 제목, 내용, 카테고리 식별키
     * @return 201 CREATED, PostResponse 객체
     */
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostCreateRequest req) {
        PostResponse response = postService.createPost(req);
        URI location = URI.create("/api/posts/" + response.getPostId());

        return ResponseEntity.created(location).body(response);
    }

    /**
     * 전체 게시글 목록 조회 API
     *
     * @param pageable 페이징 정보
     * @return 200 OK, 페이징 처리된 PostResponse 객체
     */
    @GetMapping
    public ResponseEntity<Page<PostResponse>> getPostList(@PageableDefault(sort = "createdAt") Pageable pageable) {
        Page<PostResponse> response = postService.getPostList(pageable);

        return ResponseEntity.ok(response);
    }

    /**
     * 게시글 조회 API
     *
     * @param postId 게시글 식별키
     * @return 200 OK, PostResponse 객체
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        PostResponse response = postService.getPostById(postId);

        return ResponseEntity.ok(response);
    }

    /**
     * 게시글 수정 API
     *
     * @param postId 게시글 식별키
     * @param req   제목, 내용
     * @return 200 OK
     */
    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest req) {
        postService.updatePost(postId, req);

        return ResponseEntity.ok().build();
    }

    /**
     * 게시글 삭제 API
     *
     * @param postId 게시글 식별키
     * @return 204 No Content
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);

        return ResponseEntity.noContent().build();
    }
}
