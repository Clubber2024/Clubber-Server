package com.clubber.ClubberServer.domain.recruit.dto.mainPage;

import com.clubber.ClubberServer.domain.calendar.domain.CalendarStatus;
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
public class GetOneRecruitMainPageResponse {

    @Schema(description = "club id", example = "1")
    private final Long clubId;

    @Schema(description = "모집글 id", example = "12")
    private final Long recruitId;

    @Schema(description = "모집 상태", example = "모집중")
    private final String recruitStatus;

    @Schema(description = "모집글 제목", example = "2학기 클러버 부원 모집")
    private final String title;

    @Schema(description = "모집글 생성 일자", example = "2025-01-05", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDateTime createdAt;

    public static GetOneRecruitMainPageResponse of(Recruit recruit, CalendarStatus recruitStatus) {
        return GetOneRecruitMainPageResponse.builder()
            .clubId(recruit.getClub().getId())
            .recruitId(recruit.getId())
            .recruitStatus(recruitStatus.getTitle())
            .title(recruit.getTitle())
            .createdAt(recruit.getCreatedAt())
            .build();
    }
}
