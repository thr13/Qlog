package com.qlog.backend.post.presentation;

import com.qlog.backend.post.application.CommentService;
import com.qlog.backend.post.application.LikeService;
import com.qlog.backend.post.presentation.dto.request.CommentCreateRequest;
import com.qlog.backend.post.presentation.dto.request.CommentUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;
    private final LikeService likeService;

    /**
     * 댓글 생성 API
     *
     * @param postId 게시글 ID
     * @param req    댓글 내용
     * @return 201 CREATED
     */
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Void> createComment(@PathVariable Long postId, @RequestBody CommentCreateRequest req) {
        commentService.createComment(postId, req);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 댓글 수정 API
     *
     * @param commentId 댓글 ID
     * @param req       수정할 댓글 내용
     * @return 200 OK
     */
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest req) {
        commentService.updateComment(commentId, req);

        return ResponseEntity.ok().build();
    }

    /**
     * 댓글 삭제 API
     *
     * @param commentId 댓글 ID
     * @return 204 NoContent
     */
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);

        return ResponseEntity.noContent().build();
    }

    /**
     * 댓글 좋아요 API
     *
     * @param commentId 댓글 ID
     * @return 200 OK
     */
    @PostMapping("/comments/{commentId}/like")
    public ResponseEntity<Void> likeComment(@PathVariable Long commentId) {
        likeService.likeComment(commentId);

        return ResponseEntity.ok().build();
    }

    /**
     * 댓글 좋아요 취소 API
     *
     * @param commentId 댓글 ID
     * return 204 NoContent
     */
    @DeleteMapping("/comments/{commentId}/like")
    public ResponseEntity<Void> unlikeComment(@PathVariable Long commentId) {
        likeService.unlikeComment(commentId);

        return ResponseEntity.noContent().build();
    }
}
