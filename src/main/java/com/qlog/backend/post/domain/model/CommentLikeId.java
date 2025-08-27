package com.qlog.backend.post.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLikeId implements Serializable {

    private Long profileId; //프로필 ID

    private Long commentId; //댓글 ID

    public CommentLikeId(Long profileId, Long commentId) {
        this.profileId = profileId;
        this.commentId = commentId;
    }
}
