package com.clubber.ClubberServer.domain.calender.dto;

public record CreateLinkedCalenderRequest(
        Long recruitId,
        String url
) {
}
