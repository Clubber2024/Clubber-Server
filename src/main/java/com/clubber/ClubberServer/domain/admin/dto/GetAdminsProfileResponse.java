package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GetAdminsProfileResponse(String clubName) {

    public static GetAdminsProfileResponse from(Admin admin) {
        return GetAdminsProfileResponse.builder()
                .clubName(admin.getClub().getName())
                .build();
    }
}
