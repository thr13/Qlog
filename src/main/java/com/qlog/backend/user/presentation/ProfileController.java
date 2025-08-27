package com.qlog.backend.user.presentation;

import com.qlog.backend.global.security.SecurityUser;
import com.qlog.backend.user.application.ProfileService;
import com.qlog.backend.user.presentation.dto.reqeust.ProfileUpdateRequest;
import com.qlog.backend.user.presentation.dto.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    /**
     * 로그인한 사용자 자신의 프로필 조회
     * @return 자신의 프로필 정보
     */
    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> getMyProfile() {
        ProfileResponse response = profileService.getMyProfile();
        return ResponseEntity.ok(response);
    }

    /**
     * 로그인한 사용자 자신의 프로필 수정
     * @param requestDto 수정 닉네임, 이름
     * @return 수정된 프로필 정보
     */
    @PutMapping("/me")
    public ResponseEntity<Void> updateMyProfile(@RequestBody ProfileUpdateRequest requestDto) {
        profileService.updateProfile(requestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 다른 사용자의 프로필 조회
     * @param userId 조회대상의 사용자 ID
     * @return 프로필 정보
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponse> getUserProfile(@PathVariable UUID userId) {
        ProfileResponse response = profileService.getUserProfile(userId);
        return ResponseEntity.ok(response);
    }
}
