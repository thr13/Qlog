package com.qlog.backend.global.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class SecurityUtil {

    public static UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //인증정보

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new IllegalStateException("Security Context 에 인증 정보가 없습니다.");
        }

        SecurityUser userDetails = (SecurityUser) authentication.getPrincipal(); //인증된 현재 사용자 정보
        return userDetails.getUser().getId();
    }
}
