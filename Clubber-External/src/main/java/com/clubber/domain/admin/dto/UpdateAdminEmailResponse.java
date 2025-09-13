package com.clubber.domain.admin.dto;

public record UpdateAdminEmailResponse(
        Long adminId,
        String email
) {
}
