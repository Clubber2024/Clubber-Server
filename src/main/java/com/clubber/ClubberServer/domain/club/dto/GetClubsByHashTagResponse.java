package com.clubber.ClubberServer.domain.club.dto;


import lombok.*;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubsByHashTagResponse { //각 분과별 dto
    private String hashtag;
    private List<GetClubByHashTagResponse> clubs;


    public static GetClubsByHashTagResponse of(String hashtag,List<GetClubByHashTagResponse> clubsDto){
        return GetClubsByHashTagResponse.builder()
                .hashtag(hashtag)
                .clubs(clubsDto)
                .build();
    }

}