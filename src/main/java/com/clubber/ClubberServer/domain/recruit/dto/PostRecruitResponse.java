package com.clubber.ClubberServer.domain.recruit.dto;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.global.vo.image.ImageVO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostRecruitResponse {
    private Long recruitId;
    private String title;
    private String content;
    private List<ImageVO> imageUrls;
    private Long totalView;

    public static PostRecruitResponse of(Recruit recruit, List<ImageVO> images){
        return PostRecruitResponse.builder()
                .recruitId(recruit.getId())
                .title(recruit.getTitle())
                .content(recruit.getContent())
                .imageUrls(images)
                .totalView(recruit.getTotalView())
                .build();
    }

}
