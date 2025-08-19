package com.qlog.backend.user.presentation;

import com.qlog.backend.user.application.UserService;
import com.qlog.backend.user.presentation.dto.reqeust.UserSignUpRequest;
import com.qlog.backend.user.presentation.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
     * @param userId 조회할 회원 ID
     * @return 회원 정보
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID userId) {
        UserResponse userInfo = userService.getUserInfo(userId);
        return ResponseEntity.ok(userInfo);
    }

    /**
     * 회원 가입 API
     * @param requestDto 이메일, 비밀번호, 이름, 닉네임, 성별
     * @return 회원 정보
     */
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(
            @RequestBody UserSignUpRequest requestDto
            ) {
        UserResponse response = userService.signUp(requestDto);
        URI location = URI.create("/api/users/" + response.getUserId());
        return ResponseEntity.created(location).body(response);
    }
}
