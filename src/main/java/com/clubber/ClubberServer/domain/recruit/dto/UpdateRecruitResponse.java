package com.clubber.ClubberServer.domain.recruit.dto;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateRecruitResponse {

    @Schema(description = "모집글 제목", example = "클러버 부원을 모집합니다")
    private final String title;

    @Schema(description = "모집글 내용", example = "10/22일부터 클러버 부원을 모집하고 있습니다..")
    private final String content;

    @Schema(description = "등록된 imageurls",  example = "[\"www.clubber/amazon/club/image1\",\"www.clubber/amazon/club/image3\"]")
    private final List<String> imageUrls;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDateTime updatedAt;

    public static UpdateRecruitResponse of(Recruit recruit,List<String> imageUrls){
        return UpdateRecruitResponse.builder()
                .title(recruit.getTitle())
                .content(recruit.getContent())
                .imageUrls(imageUrls)
                .updatedAt(recruit.getUpdatedAt())
                .build();
    }
}
