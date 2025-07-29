package com.clubber.ClubberServer.domain.calendar.dto;

import com.clubber.ClubberServer.domain.calendar.domain.Calendar;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import com.clubber.ClubberServer.domain.user.domain.AccountRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GetCalendarResponse(
        Long id,
        String title,
        RecruitType recruitType,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime startAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime endAt,
        String url,
        String recruitStatus,
        AccountRole writerRole
) {
    public static GetCalendarResponse from(Calendar calendar) {
        return GetCalendarResponse.builder()
                .id(calendar.getId())
                .title(calendar.getTitle())
                .recruitType(calendar.getRecruitType())
                .startAt(calendar.getStartAt())
                .endAt(calendar.getEndAt())
                .url(calendar.getUrl())
                .recruitStatus(calendar.getStatus())
                .writerRole(calendar.getWriterRole())
                .build();
    }
}
