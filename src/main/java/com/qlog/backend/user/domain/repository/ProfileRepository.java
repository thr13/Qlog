package com.qlog.backend.user.domain.repository;

import com.qlog.backend.user.domain.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUser_Id(UUID userId);
}
