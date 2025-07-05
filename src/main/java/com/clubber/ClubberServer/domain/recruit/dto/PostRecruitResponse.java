package com.clubber.ClubberServer.domain.recruit.dto;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.global.vo.image.ImageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostRecruitResponse {

    @Schema(description = "모집글 id", example = "12")
    private final Long recruitId;

    @Schema(description = "모집글 제목", example = "2학기 클러버 부원 모집")
    private final String title;

    @Schema(description = "모집 유형", example = "정규모집")
    private final String recruitType;

    @Schema(description = "모집글 내용", example = "클러버의 2학기 부원을 모집힙니다. 저희는...")
    private final String content;

    @Schema(description = "지원링크", example = "https://docs.google.com/forms")
    private final String applyLink;

    @Schema(description = "등록된 imageurls", example = "[\"www.clubber/amazon/club/image1\",\"www.clubber/amazon/club/image3\"]")
    private final List<ImageVO> imageUrls;

    @Schema(description = "캘린더 연동 여부", example = "ture")
    private Boolean isCalendarLinked;

    @Schema(description = "조회수", example = "32")
    private final Long totalView;

    public static PostRecruitResponse of(Recruit recruit, List<ImageVO> images,
        Boolean isCalendarLinked) {
        return PostRecruitResponse.builder()
            .recruitId(recruit.getId())
            .title(recruit.getTitle())
            .recruitType(recruit.getRecruitType().getTitle())
            .content(recruit.getContent())
            .applyLink(recruit.getApplyLink())
            .imageUrls(images)
            .isCalendarLinked(isCalendarLinked)
            .totalView(recruit.getTotalView())
            .build();
    }

}
