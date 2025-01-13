package com.clubber.ClubberServer.domain.recruit.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.global.vo.image.ImageVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class  GetOneRecruitWithClubResponse {

    private Long clubId;
    private String clubName;
    private String clubType;
    private ImageVO clubImage;
    private Long recruitId;
    private String title;
    private String content;
    private List<ImageVO> imageUrls;
    private Long totalView;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    public static GetOneRecruitWithClubResponse of(Recruit recruit, Club club, List<ImageVO> images) {
        return GetOneRecruitWithClubResponse.builder()
                .clubId(club.getId())
                .clubName(club.getName())
                .clubType(club.getClubType().getTitle())
                .clubImage(club.getImageUrl())
                .recruitId(recruit.getId())
                .title(recruit.getTitle())
                .content(recruit.getContent())
                .imageUrls(images)
                .totalView(recruit.getTotalView())
                .createdAt(recruit.getCreatedAt())
                .build();
    }
}