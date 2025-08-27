package com.qlog.backend.user.application;

import com.qlog.backend.global.security.SecurityUser;
import com.qlog.backend.user.domain.model.Profile;
import com.qlog.backend.user.domain.repository.ProfileRepository;
import com.qlog.backend.user.exception.NotFoundProfileException;
import com.qlog.backend.user.presentation.dto.reqeust.ProfileUpdateRequest;
import com.qlog.backend.user.presentation.dto.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    //본인 프로필 조회
    @Transactional(readOnly = true)
    public ProfileResponse getMyProfile(SecurityUser userDetails) {
        UUID userId = userDetails.getUser().getId();
        Profile profile = findProfileByUserId(userId);

        return ProfileResponse.from(profile);
    }

    //타인 프로필 조회
    @Transactional(readOnly = true)
    public ProfileResponse getUserProfile(UUID userId) {
        Profile profile = findProfileByUserId(userId);

        return ProfileResponse.from(profile);
    }

    //프로필 수정
    @Transactional
    public void updateProfile(SecurityUser userDetails, ProfileUpdateRequest requestDto) {
        UUID userId = userDetails.getUser().getId();
        Profile profile = findProfileByUserId(userId);
        profile.update(requestDto.getNickname(), requestDto.getName());
    }

    private Profile findProfileByUserId(UUID userId) {
        return profileRepository.findByUser_Id(userId).orElseThrow(
                () -> new NotFoundProfileException("해당 사용자의 프로필을 찾을 수 없습니다.")
        );
    }
}
