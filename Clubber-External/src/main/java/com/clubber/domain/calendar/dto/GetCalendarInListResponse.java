package com.clubber.domain.calendar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetCalendarInListResponse {

    @Schema(description = "모집 연도", example = "2025")
    private final int year;

    @Schema(description = "모집 월", example = "2")
    private final int month;

    @Schema(description = "정규,추가 모집 캘린더 목록")
    private final List<GetNonAlwaysCalendarResponse> nonAlwaysCalendars;

    @Schema(description = "상시 모집 캘린더 목록")
    private final List<GetAlwaysCalendarResponse> alwaysCalendars;

    public static GetCalendarInListResponse of(int year, int month,
        List<GetNonAlwaysCalendarResponse> nonAlwaysCalendars,
        List<GetAlwaysCalendarResponse> alwaysCalendars) {
        return GetCalendarInListResponse.builder()
            .year(year)
            .month(month)
            .nonAlwaysCalendars(nonAlwaysCalendars)
            .alwaysCalendars(alwaysCalendars)
            .build();
    }
}
