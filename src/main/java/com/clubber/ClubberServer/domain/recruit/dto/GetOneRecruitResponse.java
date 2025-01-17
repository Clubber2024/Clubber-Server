package com.clubber.ClubberServer.domain.recruit.dto;


import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.global.vo.image.ImageVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetOneRecruitResponse {

    @Schema(description = "club id", example = "1")
    private final Long clubId;

    @Schema(description = "모집글 id", example = "10")
    private final Long recruitId;

    @Schema(description = "모집글 제목", example = "클러버 부원 모집")
    private final String title;

    @Schema(description = "모집글 내용", example = "숭실대학교 클러버 부원 모집을 시작...")
    private final String content;

    @Schema(description = "모집글 이미지url", example = "[\"www.clubber/amazon/club/image1\",\"www.clubber/amazon/club/image2\"]")
    private final List<ImageVO> imageUrls;

    @Schema(description = "조회수", example = "32")
    private final Long totalView;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;


    public static GetOneRecruitResponse of(Recruit recruit,List<ImageVO> images){
        return GetOneRecruitResponse.builder()
                .clubId(recruit.getClub().getId())
                .recruitId(recruit.getId())
                .title(recruit.getTitle())
                .content(recruit.getContent())
                .imageUrls(images)
                .totalView(recruit.getTotalView())
                .createdAt(recruit.getCreatedAt())
                .build();
    }

}
