package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetAdminsProfileResponse {

    @Schema(description = "동아리 명", example = "club1")
    private final String clubName;

    public static GetAdminsProfileResponse from(Admin admin){
        return GetAdminsProfileResponse.builder()
                .clubName(admin.getClub().getName())
                .build();
    }
}
