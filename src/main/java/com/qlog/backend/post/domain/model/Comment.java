package com.qlog.backend.post.domain.model;

import com.qlog.backend.user.domain.model.Profile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //댓글 ID

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post; //게시글 ID, Post 테이블과 N:1 관계

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile; //프로필 ID, Profile 테이블과 N:1 관계

    @Column(nullable = false)
    private String content; //내용

    @Column(nullable = false)
    private boolean isDeleted = false; //삭제여부

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; //생성일

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt; //수정일

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLike> likes = new ArrayList<>(); //댓글 추천 테이블과 1:N 관계

    public Comment(Post post, Profile profile, String content) {
        this.profile = profile;
        this.content = content;
    }

    public void updateComment(String content) {
        this.content = content;
    }

    public void deleteComment() {
        this.isDeleted = true;
    }

}
