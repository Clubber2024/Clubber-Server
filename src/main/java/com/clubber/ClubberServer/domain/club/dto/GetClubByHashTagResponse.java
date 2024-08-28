package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.domain.ClubType;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubByHashTagResponse {

    private String clubType;
    private String division;
    private String department;
    private Long clubId;
    private String imageUrl;
    private String clubName;
    private String introduction;


    public static GetClubByHashTagResponse from(Club club){
        return GetClubByHashTagResponse.builder()
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
