package com.clubber.ClubberServer.domain.recruit.dto.recruitCalendar;

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

    @Schema(description = "모집글 목록")
    private final List<GetCalendarResponse> recruitList;

    public static GetCalendarInListResponse of(int year, int month,
        List<GetCalendarResponse> recruitList) {
        return GetCalendarInListResponse.builder()
            .year(year)
            .month(month)
            .recruitList(recruitList)
            .build();

    }

}
