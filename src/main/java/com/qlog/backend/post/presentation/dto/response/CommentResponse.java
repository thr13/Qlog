package com.qlog.backend.post.presentation.dto.response;

import com.qlog.backend.post.domain.model.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {

    private final Long commentId;
    private final String authorNickname;
    private final String content;
    private final int likeCount;
    private final LocalDateTime createdAt;

    private CommentResponse(Comment comment) {
        this.commentId = comment.getId();
        this.authorNickname = comment.getProfile().getNickname();
        this.content = comment.getContent();
        this.likeCount = comment.getLikes().size();
        this.createdAt = comment.getCreatedAt();
    }

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(comment);
    }
}
