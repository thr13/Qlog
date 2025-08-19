package com.qlog.backend.user.presentation.dto.response;

import com.qlog.backend.user.domain.model.Profile;
import lombok.Getter;

@Getter
public class ProfileResponse {
    private final String nickname;
    private final String name;
    private final String gender;

    private ProfileResponse(String nickname, String name, String gender) {
        this.nickname = nickname;
        this.name = name;
        this.gender = gender;
    }

    public static ProfileResponse from(Profile profile) {
        return new ProfileResponse(
                profile.getNickname(),
                profile.getName(),
                profile.getGender().getGender()
        );
    }
}
