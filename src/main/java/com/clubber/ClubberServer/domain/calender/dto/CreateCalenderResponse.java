package com.clubber.ClubberServer.domain.calender.dto;

import com.clubber.ClubberServer.domain.calender.entity.Calender;

public record CreateCalenderResponse(
        Long calenderId,
        String title
) {
    public static CreateCalenderResponse from(Calender calender) {
        return new CreateCalenderResponse(calender.getId(), calender.getTitle());
    }
}
