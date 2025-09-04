package com.clubber.ClubberServer.domain.calendar.dto;

import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import io.swagger.v3.oas.annotations.media.Schema;

public record GetCalendarDuplicateResponse(
        @Schema(description = "존재여부")
        boolean isExist,
        @Schema(description = "모집 종류")
        RecruitType recruitType
) {
}
