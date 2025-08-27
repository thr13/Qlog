package com.qlog.backend.user.domain.repository;

import com.qlog.backend.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * 이메일로 사용자 조회
     * @param email 사용자 이메일
     * @return Optional 로 감싼 User 객체
     */
    Optional<User> findByEmail(String email);
}
