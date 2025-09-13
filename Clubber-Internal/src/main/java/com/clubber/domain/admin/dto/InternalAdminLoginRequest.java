package com.clubber.domain.admin.dto;

public record InternalAdminLoginRequest(
        String username,
        String password
) {
}
