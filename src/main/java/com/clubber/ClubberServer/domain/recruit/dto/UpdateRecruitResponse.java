package com.clubber.ClubberServer.domain.recruit.dto;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
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

    @Schema(description = "모집 유형", example = "정규모집")
    private final String recruitType;

    @Schema(description = "모집글 내용", example = "10/22일부터 클러버 부원을 모집하고 있습니다..")
    private final String content;

    @Schema(description = "지원링크", example = "https://docs.google.com/forms")
    private final String applyLink;

    @Schema(description = "등록된 imageurls", example = "[\"https://image.ssuclubber.com/club/image1\",\"https://image.ssuclubber.com/club/image3\"]")
    private final List<String> imageUrls;

    @Schema(description = "모집글 수정 일자", example = "2025-01-05", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDateTime updatedAt;

    public static UpdateRecruitResponse of(Recruit recruit, List<String> imageUrls) {
        return UpdateRecruitResponse.builder()
            .recruitId(recruit.getId())
            .title(recruit.getTitle())
            .recruitType(recruit.getRecruitType().getTitle())
            .content(recruit.getContent())
            .applyLink(recruit.getApplyLink())
            .imageUrls(imageUrls)
            .updatedAt(recruit.getUpdatedAt())
            .build();
    }

}
