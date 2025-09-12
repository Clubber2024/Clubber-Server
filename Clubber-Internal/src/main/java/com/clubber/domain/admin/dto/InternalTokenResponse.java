package com.clubber.domain.admin.dto;

public record InternalTokenResponse(
        String accessToken,
        String refreshToken
) {
}
