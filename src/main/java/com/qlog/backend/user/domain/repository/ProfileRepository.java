package com.qlog.backend.user.domain.repository;

import com.qlog.backend.user.domain.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    /**
     * 사용자 식별키로 해당 사용자의 프로필 조회
     * @param userId 사용자 식별키
     * @return Optional 로 감싼 Profile 객체
     */
    Optional<Profile> findByUser_Id(UUID userId);
}
