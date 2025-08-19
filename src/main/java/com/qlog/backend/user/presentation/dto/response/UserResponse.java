package com.qlog.backend.user.presentation.dto.response;

import com.qlog.backend.user.domain.model.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class UserResponse {

    private final UUID userId;
    private final String email;
    private final String nickname;
    private final String name;
    private final LocalDateTime createdAt;

    private UserResponse(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getProfile().getNickname();
        this.name = user.getProfile().getName();
        this.createdAt = user.getCreatedAt();
    }

    public static UserResponse from(User user) {
        return new UserResponse(user);
    }
}
