package com.clubber.domain.admin.dto;

public record GetAdminUsernameCheckDuplicateResponse(
        String username,
        boolean isAvailable
) {
}
