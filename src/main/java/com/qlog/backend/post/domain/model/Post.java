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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //게시글 ID

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile; //프로필 ID, Profile 테이블과 N:1 관계, 관계의 주인은 Post

    @Column(length = 50, nullable = false)
    private String title; //제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; //내용

    @Column(nullable = false)
    private int view = 0; //조회수

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; //생성일

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; //수정일

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false; //삭제여부

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>(); //Comment 테이블과 1:N 관계

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files = new ArrayList<>(); //File 테이블과 1:N 관계

    @ManyToMany
    @JoinTable(
            name = "category_post",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "post")
    private List<PostLike> likes = new ArrayList<>();

    public Post(Profile profile, String title, String content) {
        this.profile = profile;
        this.title = title;
        this.content = content;
        profile.getPosts().add(this);
    }

    //게시물 업데이트
    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    //게시물 삭제
    public void deletePost() {
        this.isDeleted = true;
    }

    //조회수 증가
    public void addViewCount() {
        this.view += 1;
    }

    public void addComment(Profile author, String content) {
        Comment newComment = new Comment(this, author, content);
        this.comments.add(newComment);
    }

    //게시물 파일 추가
    public void addFile(String name, String path, Long size, String type) {
        File newFile = new File(this, name, path, size, type);
        this.files.add(newFile);
    }

    //게시물 카테고리 추가
    public void addCategory(Category category) {
        this.categories.add(category);
    }

    //게시물 카테고리 제거
    public void removeCategory(Category category) {
        this.categories.remove(category);
    }
}
