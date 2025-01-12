package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.global.vo.image.ImageVO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubSearchResponse {

    private boolean isAgreeToProvideInfo;
    private Long clubId;
    private ImageVO imageUrl;
    private String clubName;
    private String introduction;

    public static GetClubSearchResponse from (Club club){
        return GetClubSearchResponse.builder()
                .isAgreeToProvideInfo(club.isAgreeToProvideInfo())
                .clubId(club.getId())
                .imageUrl(club.getImageUrl())
                .clubName(club.getName())
                .introduction(club.getIntroduction())
                .build();
    }

}
