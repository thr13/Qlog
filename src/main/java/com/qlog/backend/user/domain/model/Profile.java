package com.qlog.backend.user.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //프로필 ID

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user; //1:1 필수관계 (관계의 주인은 Profile)

    @Column(unique = true)
    private String nickname; //닉네임

    @Column(name = "gender")
    @Setter
    private Gender gender; //성별

    @Column(length = 30)
    private String name; //이름

    public Profile() {
    }

    //성별을 선택하지 않는 경우
    public Profile(String nickname, String name) {
        this.nickname = nickname;
        this.name = name;
    }

    //성별이 포함된 경우
    public Profile(String nickname, Gender gender, String name) {
        this.nickname = nickname;
        this.gender = gender;
        this.name = name;
    }

    public void setUser(User user) {
        this.user = user;
        user.setProfile(this);
    }
}
