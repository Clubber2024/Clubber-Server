package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.domain.club.domain.*;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubResponse {
    private Long clubId;
    private String clubName;
    private ClubType clubType;
    private String introduction;
    private Hashtag hashTag;
    private Division division;
    private College college;
    private Department department;
    private String imageUrl;
    private GetClubInfoResponse clubInfo;


    public static GetClubResponse of(Club club,GetClubInfoResponse clubInfo)
    {
        return GetClubResponse.builder()
                .clubId(club.getId())
                .clubName(club.getName())
                .clubType(club.getClubType().getCode())
                .introduction(club.getIntroduction())
                .hashTag(club.getHashtag().getCode())
                .division(club.getDivision().getCode())
                .college(club.getCollege().getCode())
                .department(club.getDepartment().getCode())
                .imageUrl(club.getImageUrl())
                .clubInfo(clubInfo)
                .build();

    }
}
