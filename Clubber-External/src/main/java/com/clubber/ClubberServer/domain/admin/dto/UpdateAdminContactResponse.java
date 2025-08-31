package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.admin.domain.Contact;

public record UpdateAdminContactResponse(
        Long adminId,
        Contact contact
) {
}
