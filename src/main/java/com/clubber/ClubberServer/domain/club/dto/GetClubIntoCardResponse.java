package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.global.vo.ImageVO;

import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubIntoCardResponse { //clubId,clubName을 반환함

    private boolean isAgreeToProvideInfo;
    private Long clubId;
    private ImageVO imageUrl;
    private String clubName;
    private String introduction; //동아리에 대한 설명도 들처가야함


    public static GetClubIntoCardResponse from(Club club){
        return GetClubIntoCardResponse.builder()
                .isAgreeToProvideInfo(club.isAgreeToProvideInfo())
                .clubId(club.getId())
                .imageUrl(club.getImageUrl())
                .clubName(club.getName())
                .introduction(club.getIntroduction())
                .build();
    }

}