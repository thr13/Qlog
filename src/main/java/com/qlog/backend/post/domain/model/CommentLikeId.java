package com.qlog.backend.post.domain.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class CommentLikeId implements Serializable {

    private Long profileId; //프로필 ID
    private Long commentId; //댓글 ID

    public CommentLikeId() {
    }

    public CommentLikeId(Long profileId, Long commentId) {
        this.profileId = profileId;
        this.commentId = commentId;
    }
}
