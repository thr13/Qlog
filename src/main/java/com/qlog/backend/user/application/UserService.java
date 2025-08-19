package com.qlog.backend.user.application;

import com.qlog.backend.user.domain.model.Profile;
import com.qlog.backend.user.domain.model.User;
import com.qlog.backend.user.domain.repository.ProfileRepository;
import com.qlog.backend.user.domain.repository.UserRepository;
import com.qlog.backend.user.exception.NotFoundUserException;
import com.qlog.backend.user.presentation.dto.reqeust.UserSignUpRequest;
import com.qlog.backend.user.presentation.dto.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public UserService(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    //회원 가입
    @Transactional
    public UserResponse signUp(UserSignUpRequest requestDto) {
        User user = new User(requestDto.getEmail(), requestDto.getPassword());
        Profile profile = new Profile(requestDto.getNickname(), requestDto.getGender(), requestDto.getName());
        profile.setUser(user);
        profileRepository.save(profile);
        userRepository.save(user);
        return UserResponse.from(user);
    }

    //회원 조회
    @Transactional(readOnly = true)
    public UserResponse getUserInfo(UUID userId) {
        User user = findUser(userId);
        return UserResponse.from(user);
    }


    //회원탈퇴
    @Transactional
    public void withdrawUser(UUID userId) {
        User user = findUser(userId);
        user.withdraw();
    }

    //휴먼상태
    @Transactional
    public void dormantUser(UUID userId) {
        User user = findUser(userId);
        user.dormant();
    }

    //관리자 권한 설정
    @Transactional
    public void promoteAdmin(UUID userId) {
        User user = findUser(userId);
        user.promoteAdmin();
        log.info("관리자로 변경되었습니다");
    }

    //관리자 권한 해제
    @Transactional
    public void demoteAdmin(UUID userId) {
        User user = findUser(userId);
        user.demoteAdmin();
        log.info("관리자에서 강등되었습니다");
    }

    private User findUser(UUID userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundUserException("해당 회원을 찾을 수 없습니다")
        );
    }
}
