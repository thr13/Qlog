package com.qlog.backend.user.presentation.dto.reqeust;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ProfileUpdateRequest {

    @NotBlank
    private String nickname;
}
