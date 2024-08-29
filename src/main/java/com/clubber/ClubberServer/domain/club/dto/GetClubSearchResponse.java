package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubSearchResponse {

    private String division;
    private String department;
    private Long clubId;
    private String imageUrl;
    private String clubName;
    private String introduction;


    public static GetClubSearchResponse from (Club club){
        return GetClubSearchResponse.builder()
                .division(club.getDivision().getTitle())
                .department(club.getDepartment().getTitle())
                .clubId(club.getId())
                .imageUrl(club.getImageUrl())
                .clubName(club.getName())
                .introduction(club.getIntroduction())
                .build();
    }
}
