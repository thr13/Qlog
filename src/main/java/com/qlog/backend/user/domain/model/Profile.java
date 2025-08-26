package com.qlog.backend.user.domain.model;

import com.qlog.backend.post.domain.model.CommentLike;
import com.qlog.backend.post.domain.model.Post;
import com.qlog.backend.post.domain.model.PostLike;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "profiles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //프로필 ID

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user; //1:1 필수관계 (관계의 주인은 Profile)

    @Column(unique = true, nullable = false)
    @Setter
    private String nickname; //닉네임

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    @Setter
    private Gender gender; //성별

    @Column(length = 30, nullable = false)
    private String name; //이름

    @OneToMany(mappedBy = "profile")
    private List<Post> posts = new ArrayList<>(); //Post 테이블과 1:N 관계, 관계의 주인은 Post

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> postLikes = new ArrayList<>(); //1:N 게시글 추천

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLike> commentLikes = new ArrayList<>(); //1:N 댓글 추천

    public Profile(String nickname, Gender gender, String name) {
        this.nickname = nickname;
        this.gender = gender;
        this.name = name;
    }

    //프로필 업데이트
    public void updateProfile(String nickname, String name) {
        this.nickname = nickname;
        this.name = name;
    }

    //닉네임 변경
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    //User 와 양방향 관계
    public void setUser(User user) {
        this.user = user;
        user.setProfile(this);
    }

}
