package com.clubber.ClubberServer.domain.recruit.dto;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitImage;
import com.clubber.ClubberServer.global.vo.image.ImageVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateRecruitResponse {

    @Schema(description = "모집글 id", example = "12")
    private final Long recruitId;

    @Schema(description = "모집글 제목", example = "클러버 부원을 모집합니다")
    private final String title;

    @Schema(description = "모집글 내용", example = "10/22일부터 클러버 부원을 모집하고 있습니다..")
    private final String content;

    @Schema(description = "등록된 imageurls", example = "[\"https://image.ssuclubber.com/club/image1\",\"https://image.ssuclubber.com/club/image3\"]")
    private final List<ImageVO> imageUrls;

    @Schema(description = "모집글 수정 일자", example = "2025-01-05", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDateTime updatedAt;

    public static UpdateRecruitResponse of(Recruit recruit, List<RecruitImage> images) {
        return UpdateRecruitResponse.builder()
            .recruitId(recruit.getId())
            .title(recruit.getTitle())
            .content(recruit.getContent())
            .imageUrls(images.stream()
                .map(RecruitImage::getImageUrl)
                .collect(Collectors.toList()))
            .updatedAt(recruit.getUpdatedAt())
            .build();
    }

}
