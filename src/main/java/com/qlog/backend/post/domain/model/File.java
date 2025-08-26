package com.qlog.backend.post.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "files")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //파일 ID

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post; //Post 테이블과 N:1 관계

    @Column(nullable = false)
    private String name; //파일 이름

    @Column(nullable = false, length = 512)
    private String path; //파일 경로

    @Column(nullable = false)
    private Long size; //파일 크기

    @Column(length = 50)
    private String type; //파일 타입(확장자)

    @Column(nullable = false)
    private boolean isDeleted = false; //삭제 여부

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; //생성일

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt; //수정일

    public File(Post post, String name, String path, Long size, String type) {
        this.post = post;
        this.name = name;
        this.path = path;
        this.size = size;
        this.type = type;
    }

    public void delete() {
        this.isDeleted = true;
    }

}
