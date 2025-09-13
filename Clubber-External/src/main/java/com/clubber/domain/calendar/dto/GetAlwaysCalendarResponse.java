package com.clubber.domain.calendar.dto;

import com.clubber.domain.recruit.domain.RecruitType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GetAlwaysCalendarResponse {

    @Schema(description = "동아리 id", example = "1")
    private final Long clubId;

    @Schema(description = "동아리명", example = "클러버")
    private final String clubName;

    @Schema(description = "모집 유형", example = "수시모집")
    private final String recruitType;

    @Schema(description = "모집글 개수", example = "2")
    private final Long calendarNum;

    public GetAlwaysCalendarResponse(Long clubId, String clubName, Long calendarNum) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.recruitType = RecruitType.ALWAYS.getTitle();
        this.calendarNum = calendarNum;
    }

}
