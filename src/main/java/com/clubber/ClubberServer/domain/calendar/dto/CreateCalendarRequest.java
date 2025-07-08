package com.clubber.ClubberServer.domain.calendar.dto;

import static com.clubber.ClubberServer.domain.club.domain.QClub.club;

import com.clubber.ClubberServer.domain.calendar.entity.Calendar;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import com.clubber.ClubberServer.domain.user.domain.AccountRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CreateCalendarRequest(
    @NotBlank(message = "제목을 입력해주세요")
    String title,
    @NotBlank(message = "모집 종류를 입력해주세요")
    RecruitType recruitType,
    @NotBlank(message = "시작 일자를 입력해주세요")
    LocalDateTime startAt,
    @NotBlank(message = "마감 일정을 입력해주세요")
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

    public static CreateCalendarRequest from(Recruit recruit, String recruitUrl) {
        return CreateCalendarRequest.builder()
            .title(recruit.getTitle())
            .recruitType(recruit.getRecruitType())
            .startAt(recruit.getStartAt())
            .endAt(recruit.getEndAt())
            .url(recruitUrl)
            .build();
    }
}
