package com.clubber.ClubberServer.domain.recruit.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRecruitRequest {

    @NotBlank
    @Schema(description = "모집글 제목", example = "클러버 부원을 모집합니다")
    private String title;

    @NotBlank
    @Schema(description = "모집글 내용", example = "10/22일부터 클러버 부원을 모집하고 있습니다..")
    private String content;

    @Schema(description = "에브리타임 링크", example = "https://everytime.kr/recruit")
    private String everytimeUrl;

    @Schema(description = "모집글 이미지 목록", example = "[\"image1\",\"image2\"]")
    private List<String> imageKey;

    public Recruit toEntity(Club club) {
        return Recruit.builder()
                .title(title)
                .content(content)
                .everytimeUrl(everytimeUrl)
                .club(club)
                .build();
    }
}
