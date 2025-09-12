package com.clubber.domain.admin.dto;

import com.clubber.domain.admin.domain.Admin;
import com.clubber.domain.admin.domain.Contact;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GetAdminsProfileResponse(
        String username,
        String email,
        Contact contact) {

    public static GetAdminsProfileResponse from(Admin admin) {
        return GetAdminsProfileResponse.builder()
                .username(admin.getUsername())
                .email(admin.getEmail())
                .contact(admin.getContact())
                .build();
    }
}
