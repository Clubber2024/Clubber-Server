package com.clubber.ClubberServer.domain.admin.dto;

public record UpdateAdminEmailResponse(
        Long adminId,
        String email
) {
}
