package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetAdminsProfileResponse {

    private final String clubName;

    public static GetAdminsProfileResponse from(Admin admin){
        return GetAdminsProfileResponse.builder()
                .clubName(admin.getClub().getName())
                .build();
    }
}
