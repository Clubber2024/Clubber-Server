package com.clubber.domain.internaladmin.dto;

public record InternalAdminLoginRequest(
        String username,
        String password
) {
}
