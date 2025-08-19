package com.qlog.backend.user.domain.model;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE("활성"),
    WITHDRAW("탈퇴"),
    DORMANT("휴먼");

    private final String state;

    UserStatus(String state) {
        this.state = state;
    }
}
