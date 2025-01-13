package com.clubber.ClubberServer.domain.recruit.dto;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.global.vo.image.ImageVO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetOneRecruitInListResponse {

    private Long recruitId;
    private String title;
    private String content;
    private ImageVO imageUrl;

    public static GetOneRecruitInListResponse of(Recruit recruit,String content,ImageVO imageUrl){
        return GetOneRecruitInListResponse.builder()
                .recruitId(recruit.getId())
                .title(recruit.getTitle())
                .content(content)
                .imageUrl(imageUrl)
                .build();
    }
}
