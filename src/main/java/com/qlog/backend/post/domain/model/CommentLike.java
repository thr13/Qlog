package com.qlog.backend.post.domain.model;

import com.qlog.backend.user.domain.model.Profile;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "comment_like")
public class CommentLike {

    @EmbeddedId
    private CommentLikeId id; //댓글 추천

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile; //프로필

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment; //댓글

    public CommentLike() {
    }

    public CommentLike(Profile profile, Comment comment) {
        this.id = new CommentLikeId(profile.getId(), comment.getId());
        this.profile = profile;
        this.comment = comment;
    }
}
