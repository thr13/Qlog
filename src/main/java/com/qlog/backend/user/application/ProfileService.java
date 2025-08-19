package com.qlog.backend.user.application;

import com.qlog.backend.user.domain.model.Profile;
import com.qlog.backend.user.domain.repository.ProfileRepository;
import com.qlog.backend.user.exception.NotFoundProfileException;
import com.qlog.backend.user.presentation.dto.reqeust.ProfileUpdateRequest;
import com.qlog.backend.user.presentation.dto.response.ProfileResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    //프로필 조회
    @Transactional(readOnly = true)
    public ProfileResponse getProfile(UUID userId) {
        Profile profile = findProfileByUserId(userId);
        return ProfileResponse.from(profile);
    }

    //프로필 수정
    @Transactional
    public void updateProfile(UUID userId, ProfileUpdateRequest requestDto) {
        Profile profile = findProfileByUserId(userId);
        profile.setNickname(requestDto.getNickname());
    }

    private Profile findProfileByUserId(UUID userId) {
        return profileRepository.findByUser_Id(userId).orElseThrow(
                () -> new NotFoundProfileException("해당 사용자의 프로필을 찾을 수 없습니다.")
        );
    }
}
