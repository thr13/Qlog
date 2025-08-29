package com.qlog.backend.post.presentation;

import com.qlog.backend.post.application.LikeService;
import com.qlog.backend.post.application.PostService;
import com.qlog.backend.post.presentation.dto.request.PostCreateRequest;
import com.qlog.backend.post.presentation.dto.request.PostUpdateRequest;
import com.qlog.backend.post.presentation.dto.response.CommentResponse;
import com.qlog.backend.post.presentation.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final LikeService likeService;

    /**
     * 게시글 생성 API
     * @param request 제목, 내용, 카테고리 ID
     * @param files 첨부 파일
     * @return 201 CREATED, PostResponse 객체
     */
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<PostResponse> createPost(
            @RequestPart("request") PostCreateRequest request,
            @RequestPart(value = "files", required = false) List<MultipartFile> files)
    {
        PostResponse response = postService.createPost(request, files);
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
     * @param postId 게시글 ID
     * @return 200 OK, PostResponse 객체
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        PostResponse response = postService.getPostById(postId);

        return ResponseEntity.ok(response);
    }

    /**
     * 게시글에 달린 댓글 조회 API
     *
     * @param postId   게시글 ID
     * @param pageable 페이징 정보
     * @return 200 OK, 페이징 처리된 CommentResponse 객체
     */
    @GetMapping("/{postId}/comments")
    public ResponseEntity<Page<CommentResponse>> getComments(
            @PathVariable Long postId,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
        Page<CommentResponse> response = postService.getCommentsByPost(postId, pageable);

        return ResponseEntity.ok(response);
    }

    /**
     * 게시글 수정 API
     *
     * @param postId 게시글 ID
     * @param req    제목, 내용
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
     * @param postId 게시글 ID
     * @return 204 No Content
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);

        return ResponseEntity.noContent().build();
    }

    /**
     * 게시글 좋아요 API
     *
     * @param postId 게시글 ID
     * @return 200 OK
     */
    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> likePost(@PathVariable Long postId) {
        likeService.likePost(postId);

        return ResponseEntity.ok().build();
    }

    /**
     * 게시글 좋아요 취소 API
     *
     * @param postId 게시글 ID
     * @return 204 NoContent
     */
    @DeleteMapping("/{postId}/like")
    public ResponseEntity<Void> unlikePost(@PathVariable Long postId) {
        likeService.unlikePost(postId);

        return ResponseEntity.noContent().build();
    }
}
