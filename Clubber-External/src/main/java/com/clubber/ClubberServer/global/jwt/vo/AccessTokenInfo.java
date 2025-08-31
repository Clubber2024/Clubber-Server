package com.clubber.ClubberServer.global.jwt.vo;

import lombok.Builder;

@Builder
public record AccessTokenInfo(Long userId, String role) {
}
