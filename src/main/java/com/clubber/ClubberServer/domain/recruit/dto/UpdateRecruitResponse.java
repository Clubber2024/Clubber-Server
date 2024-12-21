package com.clubber.ClubberServer.domain.recruit.dto;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.global.vo.ImageVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateRecruitResponse {
    @NotNull
    private String title;
    @NotNull
    private String content;

    private List<String> imageUrls;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime updatedAt;

    public static UpdateRecruitResponse of(Recruit recruit,List<String> imageUrls){
        return UpdateRecruitResponse.builder()
                .title(recruit.getTitle())
                .content(recruit.getContent())
                .imageUrls(imageUrls)
                .updatedAt(recruit.getUpdatedAt())
                .build();
    }
}
