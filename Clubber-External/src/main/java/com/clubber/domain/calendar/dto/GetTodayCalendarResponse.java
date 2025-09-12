package com.clubber.domain.calendar.dto;

import com.clubber.domain.domains.club.domain.Club;
import io.swagger.v3.oas.annotations.media.Schema;

public record GetTodayCalendarResponse(
        @Schema(description = "동아리 id")
        Long clubId,
        @Schema(description = "동아리 이름")
        String clubName
) {
    public static GetTodayCalendarResponse from(Club club) {
        return new GetTodayCalendarResponse(club.getId(), club.getName());
    }
}
