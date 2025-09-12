package com.clubber.domain.club.dto;

import com.clubber.domain.domains.club.domain.Division;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetSummaryClubGroupResponse {

    @Schema(description = "분과", example = "연대사업분과")
    private final String division;

    @Schema(description = "동아리 ID 및 동아리명", example = "{\"clubId\": 1, \"clubName\": \"클러버\"}")
    private final List<GetSummaryClubResponse> clubs;

    public static GetSummaryClubGroupResponse of(Division division,
        List<GetSummaryClubResponse> clubs) {
        return GetSummaryClubGroupResponse.builder()
            .division(division.getTitle())
            .clubs(clubs)
            .build();
    }

}
