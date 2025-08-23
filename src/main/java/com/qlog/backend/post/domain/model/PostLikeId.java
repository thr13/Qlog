package com.qlog.backend.post.domain.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class PostLikeId {

    private Long profileId; //프로필 ID
    private Long postId; //게시글 ID

    public PostLikeId() {
    }

    public PostLikeId(Long profileId, Long postId) {
        this.profileId = profileId;
        this.postId = postId;
    }
}
