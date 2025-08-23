package com.qlog.backend.post.domain.model;

import com.qlog.backend.user.domain.model.Profile;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //게시글 ID

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile; //프로필 ID, Profile 테이블과 N:1 관계, 관계의 주인은 Post

    @Column(length = 50, nullable = false)
    private String title; //제목

    @Column(nullable = false)
    private String content; //내용

    @Column(nullable = false)
    private LocalDateTime createdAt; //생성일

    @Column(nullable = false)
    private LocalDateTime updatedAt; //수정일

    @Column(nullable = false)
    private boolean isDeleted; //삭제여부

    @Column(nullable = false)
    private int view; //조회수

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<File> files = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "category_post",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "post")
    private List<PostLike> likes = new ArrayList<>();

    public Post() {
    }

    public Post(Profile profile, String title, String content) {
        this.profile = profile;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isDeleted = false;
        this.view = 0;
    }

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    public void deletePost() {
        this.isDeleted = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void addViewCount() {
        this.view += 1;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
    }
}
