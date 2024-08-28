package com.clubber.ClubberServer.domain.club.dto;


import com.clubber.ClubberServer.domain.club.domain.Hashtag;
import lombok.*;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubsByHashTagResponse { //각 분과별 dto
    private Hashtag hashtag;
    private List<GetClubByHashTagResponse> clubs;


    public static GetClubsByHashTagResponse of(Hashtag hashtag,List<GetClubByHashTagResponse> clubsDto){
        return GetClubsByHashTagResponse.builder()
                .hashtag(hashtag.getCode())
                .clubs(clubsDto)
                .build();
    }

}