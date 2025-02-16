package com.clubber.ClubberServer.domain.recruit.dto;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.global.vo.image.ImageVO;
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
public class DeleteRecruitByIdResponse {

    @Schema(description = "메세지", example = "해당 모집글이 삭제되었습니다.")
    private final String message;

    @Schema(description = "club id", example = "1")
    private final Long clubId;

    @Schema(description = "모집글 id", example = "10")
    private final Long recruitId;

    @Schema(description = "모집글 제목", example = "클러버 부원 모집")
    private final String title;

    @Schema(description = "모집글 내용", example = "숭실대학교 클러버 부원 모집을 시작...")
    private final String content;

    @Schema(description = "에브리타임 링크", example = "https://everytime.kr/recruit")
    private final String everytimeUrl;

    @Schema(description = "삭제된 imageurls", example = "[\"https://image.ssuclubber.com/club/image1\",\"https://image.ssuclubber.com/club/image3\"]")
    private final List<ImageVO> imageUrls;

    @Schema(description = "조회수", example = "32")
    private final Long totalView;

    @Schema(description = "모집글 생성 일자", example = "2025-01-05", type = "string")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDateTime createdAt;

    public static DeleteRecruitByIdResponse from(Recruit recruit, List<ImageVO> images) {
        return DeleteRecruitByIdResponse.builder()
            .message("해당 모집글이 삭제되었습니다.")
            .clubId(recruit.getClub().getId())
            .recruitId(recruit.getId())
            .title(recruit.getTitle())
            .content(recruit.getContent())
            .everytimeUrl(recruit.getEverytimeUrl())
            .imageUrls(images)
            .totalView(recruit.getTotalView())
            .createdAt(recruit.getCreatedAt())
            .build();
    }

}