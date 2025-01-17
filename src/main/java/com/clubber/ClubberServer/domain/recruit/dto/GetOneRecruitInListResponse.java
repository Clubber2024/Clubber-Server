package com.clubber.ClubberServer.domain.recruit.dto;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.global.vo.image.ImageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetOneRecruitInListResponse {

    @Schema(description = "모집글 id", example = "10")
    private final Long recruitId;

    @Schema(description = "모집글 제목", example = "클러버 부원 모집")
    private final String title;

    @Schema(description = "모집글 내용", example = "숭실대학교 클러버 부원 모집을 시작...")
    private final String content;

    @Schema(description = "모집글 대표 이미지", example = "www.clubber/amazon/club/image2")
    private final ImageVO imageUrl;

    public static GetOneRecruitInListResponse of(Recruit recruit,String content,ImageVO imageUrl){
        return GetOneRecruitInListResponse.builder()
                .recruitId(recruit.getId())
                .title(recruit.getTitle())
                .content(content)
                .imageUrl(imageUrl)
                .build();
    }
}
