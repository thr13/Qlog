package com.qlog.backend.post.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikeId implements Serializable {

    private Long profileId; //프로필 ID

    private Long postId; //게시글 ID

    public PostLikeId(Long profileId, Long postId) {
        this.profileId = profileId;
        this.postId = postId;
    }
}
