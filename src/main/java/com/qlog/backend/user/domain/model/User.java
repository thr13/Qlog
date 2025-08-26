package com.qlog.backend.user.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.BINARY) //자바의 UUID 를 DB 의 BINARY 타입으로 변환시켜줌
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id; //사용자 ID

    @Column(nullable = false, unique = true)
    private String email; //이메일

    @Column(nullable = false)
    private String password; //비밀번호

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; //생성일

    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin = false; //관리자여부

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status = UserStatus.ACTIVE;; //계정상태

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Profile profile; //1:1 필수관계(관계의 주인은 Profile)

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    protected void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void promoteAdmin() {
        this.isAdmin = true;
    }

    public void demoteAdmin() {
        this.isAdmin = false;
    }

    public void withdraw() {
        this.status = UserStatus.WITHDRAW;
    }

    public void dormant() {
        this.status = UserStatus.DORMANT;
    }
}
