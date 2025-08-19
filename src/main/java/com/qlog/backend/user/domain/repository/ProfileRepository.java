package com.qlog.backend.user.domain.repository;

import com.qlog.backend.user.domain.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
