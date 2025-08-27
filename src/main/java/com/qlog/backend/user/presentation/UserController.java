package com.qlog.backend.user.presentation;

import com.qlog.backend.global.security.SecurityUser;
import com.qlog.backend.user.application.UserService;
import com.qlog.backend.user.presentation.dto.reqeust.UserSignUpRequest;
import com.qlog.backend.user.presentation.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * 사용자 정보 조회 API
     * @param userId 조회할 사용자 ID
     * @return 사용자 정보
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID userId) {
        UserResponse userInfo = userService.getUserInfo(userId);
        return ResponseEntity.ok(userInfo);
    }

    /**
     * 회원 가입 API
     * @param requestDto 이메일, 비밀번호, 이름, 닉네임, 성별
     * @return 사용자 정보
     */
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(@RequestBody UserSignUpRequest requestDto) {
        UserResponse response = userService.signUp(requestDto);
        URI location = URI.create("/api/users/" + response.getUserId());
        return ResponseEntity.created(location).body(response);
    }

    /**
     * 회원 탈퇴 API
     * @param userDetails 현재 인증된 사용자 정보
     * @return 200
     */
    @DeleteMapping("/me")
    public ResponseEntity<Void> withdrawMyAccount(@AuthenticationPrincipal SecurityUser userDetails) {
        UUID userId = userDetails.getUser().getId();
        userService.withdrawUser(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * 휴면 상태 변경 API
     * @param userId 사용자 ID
     * @return 200
     */
    @PostMapping("/{userId}/dormant")
    public ResponseEntity<Void> makeUserDormant(@PathVariable UUID userId) {
        userService.dormantUser(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * 관리자 권한 설정 API
     * @param userId 사용자 ID
     * @return 200
     */
    @PostMapping("/{userId}/admin")
    public ResponseEntity<Void> promoteAdmin(@PathVariable UUID userId) {
        userService.promoteAdmin(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * 관리자 권한 해제 API
     * @param userId 사용자 ID
     * @return 200
     */
    @DeleteMapping("/{userId}/admin")
    public ResponseEntity<Void> demoteAdmin(@PathVariable UUID userId) {
        userService.demoteAdmin(userId);
        return ResponseEntity.ok().build();
    }
}
