package com.qlog.backend.post.domain.model;

import com.qlog.backend.user.domain.model.Profile;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "post_like")
public class PostLike {

    @EmbeddedId
    private PostLikeId id; //게시글 추천

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public PostLike() {
    }

    public PostLike(Profile profile, Post post) {
        this.id = new PostLikeId(profile.getId(), post.getId());
        this.profile = profile;
        this.post = post;
    }
}
