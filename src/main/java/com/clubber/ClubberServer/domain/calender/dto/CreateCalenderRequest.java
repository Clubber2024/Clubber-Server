package com.clubber.ClubberServer.domain.calender.dto;

import com.clubber.ClubberServer.domain.calender.entity.Calender;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import com.clubber.ClubberServer.domain.user.domain.AccountRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CreateCalenderRequest(
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
    public Calender toEntity() {
        return Calender.builder()
                .title(title)
                .recruitType(recruitType)
                .startAt(startAt)
                .endAt(endAt)
                .url(url)
                .writerRole(AccountRole.ADMIN)
                .build();
    }

    public static CreateCalenderRequest from(Calender calender, String recruitUrl) {
        return CreateCalenderRequest.builder()
                .title(calender.getTitle())
                .recruitType(calender.getRecruitType())
                .startAt(calender.getStartAt())
                .endAt(calender.getEndAt())
                .url(recruitUrl)
                .build();
    }
}
