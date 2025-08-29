package com.clubber.ClubberServer.domain.admin.dto;

public record GetAdminUsernameCheckDuplicateResponse(
        String username,
        boolean isAvailable
) {
}
