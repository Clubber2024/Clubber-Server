package com.clubber.domain.calendar.dto;

import com.clubber.domain.calendar.domain.Calendar;
import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.recruit.domain.RecruitType;
import com.clubber.domain.domains.user.domain.AccountRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CreateCalendarRequest(
        @Schema(description = "캘린더 제목")
        @NotBlank(message = "제목을 입력해주세요")
        String title,
        @Schema(description = "모집 유형")
        @NotNull(message = "모집 종류를 입력해주세요")
        RecruitType recruitType,
        @Schema(description = "기간 시작일", example = "2025-08-30 00:00", type = "string")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime startAt,
        @Schema(description = "기간 마감일", example = "2025-09-02 23:59", type = "string")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime endAt,
        String url

) {
    public Calendar toEntity(Club club) {
        return Calendar.builder()
                .title(title)
                .recruitType(recruitType)
                .startAt(startAt)
                .endAt(endAt)
                .url(url)
                .writerRole(AccountRole.ADMIN)
                .club(club)
                .build();
    }
}
