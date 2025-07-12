package com.clubber.ClubberServer.domain.calendar.dto;

import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;

public record GetCalendarDuplicateResponse(
        boolean isExist,
        RecruitType recruitType,
        int year,
        int month
) {
}
