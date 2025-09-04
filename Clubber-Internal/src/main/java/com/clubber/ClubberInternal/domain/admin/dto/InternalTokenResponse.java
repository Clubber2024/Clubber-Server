package com.clubber.ClubberInternal.domain.admin.dto;

public record InternalTokenResponse(
        String accessToken,
        String refreshToken
) {
}
