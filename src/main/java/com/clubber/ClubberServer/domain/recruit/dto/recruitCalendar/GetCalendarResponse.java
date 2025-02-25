package com.clubber.ClubberServer.domain.recruit.dto.recruitCalendar;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
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
public class GetCalendarResponse {

    @Schema(description = "동아리 id", example = "1")
    private final Long clubId;

    @Schema(description = "동아리명", example = "클러버")
    private final String clubName;

    @Schema(description = "에브리타임 url", example = "https://www.everytime.com")
    private final String everytimeUrl;

    @Schema(description = "모집시기", example = "ALWAYS")
    private final String semester;

    @Schema(description = "모집 시작 일자", example = "2025-02-05 00:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime startAt;

    @Schema(description = "모집 마감 일자", example = "2025-02-25 23:59:59")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime endAt;

    public static GetCalendarResponse of(Recruit recruit) {
        return GetCalendarResponse.builder()
            .clubId(recruit.getClub().getId())
            .clubName(recruit.getClub().getName())
            .everytimeUrl(recruit.getEverytimeUrl())
            .semester(recruit.getSemester().name())
            .startAt(recruit.getStartAt())
            .endAt(recruit.getEndAt())
            .build();
    }

}
