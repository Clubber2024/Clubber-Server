package com.clubber.ClubberServer.domain.calendar.dto;

import com.clubber.ClubberServer.domain.calendar.domain.Calendar;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import com.clubber.ClubberServer.domain.user.domain.AccountRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GetCalendarResponseWithLinkedStatus(
        Long id,
        String title,
        RecruitType recruitType,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime startAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime endAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime createdAt,
        String url,
        String recruitStatus,
        AccountRole writerRole,
        boolean isCalendarLinked
) {
    public static GetCalendarResponseWithLinkedStatus from(Calendar calendar, boolean isCalendarLinked) {
        return GetCalendarResponseWithLinkedStatus.builder()
                .id(calendar.getId())
                .title(calendar.getTitle())
                .recruitType(calendar.getRecruitType())
                .startAt(calendar.getStartAt())
                .endAt(calendar.getEndAt())
                .createdAt(calendar.getCreatedAt())
                .url(calendar.getUrl())
                .recruitStatus(calendar.getStatus())
                .writerRole(calendar.getWriterRole())
                .isCalendarLinked(isCalendarLinked)
                .build();
    }
}
