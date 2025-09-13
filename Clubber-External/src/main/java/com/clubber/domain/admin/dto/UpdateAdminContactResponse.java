package com.clubber.domain.admin.dto;

import com.clubber.domain.domains.admin.domain.Contact;

public record UpdateAdminContactResponse(
        Long adminId,
        Contact contact
) {
}
