package com.clubber.ClubberServer.domain.recruit.dto;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetRecruitResponse {
    private Long recruitId;
    private String title;
    private String content;
    private Long totalView;
    private LocalDateTime createdAt;

    public static GetRecruitResponse from(Recruit recruit){
        return GetRecruitResponse.builder()
                .recruitId(recruit.getId())
                .title(recruit.getTitle())
                .content(recruit.getContent())
                .totalView(recruit.getTotalView())
                .createdAt(recruit.getCreatedAt())
                .build();
    }
}
