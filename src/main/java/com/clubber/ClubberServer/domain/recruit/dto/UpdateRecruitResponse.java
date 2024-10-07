package com.clubber.ClubberServer.domain.recruit.dto;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.global.vo.ImageVO;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateRecruitResponse {
    @NotNull
    private String title;
    @NotNull
    private String content;

    private List<String> imageUrl;

    public static UpdateRecruitResponse of(Recruit recruit,List<ImageVO> images){
        return UpdateRecruitResponse.builder()
                .title(recruit.getTitle())
                .content(recruit.getContent())
                .images(images)
                .updatedAt(recruit.getUpdatedAt())
                .build();
    }
}
