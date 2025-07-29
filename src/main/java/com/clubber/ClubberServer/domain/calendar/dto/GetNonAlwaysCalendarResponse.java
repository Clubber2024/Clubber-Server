package com.clubber.ClubberServer.domain.calendar.dto;

import com.clubber.ClubberServer.domain.calendar.domain.Calendar;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetNonAlwaysCalendarResponse {

    @Schema(description = "동아리 id", example = "1")
    private final Long clubId;

    @Schema(description = "동아리명", example = "클러버")
    private final String clubName;

    @Schema(description = "모집 유형", example = "정규모집")
    private final String recruitType;

    @Schema(description = "모집 시작 일자", example = "2025-02-05 00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime startAt;

    @Schema(description = "모집 마감 일자", example = "2025-02-25 23:59:59")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime endAt;

    public static GetNonAlwaysCalendarResponse from(Calendar calendar) {
        return GetNonAlwaysCalendarResponse.builder()
            .clubId(calendar.getClub().getId())
            .clubName(calendar.getClub().getName())
            .recruitType(calendar.getRecruitType().getTitle())
            .startAt(calendar.getStartAt())
            .endAt(calendar.getEndAt())
            .build();
    }

}