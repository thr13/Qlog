package com.qlog.backend.user.domain.model;

import lombok.Getter;

@Getter
public enum Gender {
    M("남성"),
    F("여성");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }
}
