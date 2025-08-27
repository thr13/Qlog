package com.qlog.backend.post.presentation.dto.response;

import com.qlog.backend.post.domain.model.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponse {

    private final Long postId;
    private final String authorNickname;
    private final String title;
    private final String content;
    private final int viewCount;
    private final int likeCount;
    private final LocalDateTime createdAt;

    private PostResponse(Post post) {
        this.postId = post.getId();
        this.authorNickname = post.getProfile().getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.viewCount = post.getView();
        this.likeCount = post.getLikes().size();
        this.createdAt = post.getCreatedAt();
    }

    public static PostResponse from(Post post) {
        return new PostResponse(post);
    }
}
