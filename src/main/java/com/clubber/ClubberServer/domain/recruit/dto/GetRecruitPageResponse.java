package com.clubber.ClubberServer.domain.recruit.dto;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetRecruitPageResponse {
    private Long recruitId;
    private String title;
    private String content;
    private String imageUrl;
    private Long totalView;

    public static GetRecruitPageResponse of(Recruit recruit){
        return GetRecruitPageResponse.builder()
                .recruitId(recruit.getId())
                .title(recruit.getTitle())
                .content(recruit.getContent())
                .imageUrl(recruit.getImageUrl())
                .totalView(recruit.getTotalView())
                .build();
    }
}
