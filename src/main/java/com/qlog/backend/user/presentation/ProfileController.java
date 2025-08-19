package com.qlog.backend.user.presentation;

import com.qlog.backend.user.application.ProfileService;
import com.qlog.backend.user.presentation.dto.reqeust.ProfileUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PutMapping("/api/profiles/{userId}")
    public ResponseEntity<Void> updateProfile(
            @PathVariable UUID userId,
            @RequestBody ProfileUpdateRequest requestDto
            ) {
        profileService.updateProfile(userId, requestDto);
        return ResponseEntity.ok().build();
    }
}
