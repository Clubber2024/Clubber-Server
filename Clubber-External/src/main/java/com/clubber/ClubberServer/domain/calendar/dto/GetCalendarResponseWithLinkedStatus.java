package com.clubber.ClubberServer.domain.calendar.dto;

import com.clubber.ClubberServer.domain.calendar.domain.Calendar;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import com.clubber.domain.domains.user.domain.AccountRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GetCalendarResponseWithLinkedStatus(
        @Schema(description = "캘린더 id", example = "1")
        Long id,
        @Schema(description = "캘린더 제목")
        String title,
        @Schema(description = "모집 유형")
        RecruitType recruitType,
        @Schema(description = "시작일")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime startAt,
        @Schema(description = "마감일")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime endAt,
        @Schema(description = "등록일")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime createdAt,
        @Schema(description = "모집 URL")
        String url,
        @Schema(description = "현재 모집 상태", example = "모집전,진행중,마감됨")
        String recruitStatus,
        @Schema(description = "작성자 유형")
        AccountRole writerRole,
        @Schema(description = "모집글과 연동 여부")
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
