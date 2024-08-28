package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubSearchResponse {
    private String clubType;
    private String division;
    private String department;
    private Long clubId;
    private String imageUrl;
    private String clubName;
    private String introduction;


    public static GetClubSearchResponse from (Club club){
        return GetClubSearchResponse.builder()
                .clubType(club.getClubType().getCode().toLowerCase())
                .division(club.getDivision().getCode())
                .department(club.getDepartment().getCode())
                .clubId(club.getId())
                .imageUrl(club.getImageUrl())
                .clubName(club.getName())
                .introduction(club.getIntroduction())
                .build();
    }
}
