package com.qlog.backend.user.domain.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id; //사용자 ID

    @Column(nullable = false, unique = true)
    private String email; //이메일

    @Column(nullable = false)
    private String password; //비밀번호

    @Column(nullable = false)
    private LocalDateTime createdAt; //생성일

    @Column(nullable = false)
    private boolean isAdmin; //관리자여부

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status; //계정상태

    @OneToOne(mappedBy = "user")
    private Profile profile; //1:1 필수관계(관계의 주인은 Profile)

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.isAdmin = false;
        this.createdAt = LocalDateTime.now();
        this.status = UserStatus.ACTIVE;
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
