package com.clubber.ClubberServer.domain.club.dto;


import com.clubber.ClubberServer.domain.club.domain.ClubType;
import com.clubber.ClubberServer.domain.club.domain.Hashtag;
import lombok.*;

import java.util.EnumMap;
import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubsByHashTagResponse { //각 분과별 dto
    private String hashtag;

    EnumMap<ClubType, List<GetClubByHashTagResponse>> clubs;

    public static GetClubsByHashTagResponse of (Hashtag hashtag,EnumMap<ClubType, List<GetClubByHashTagResponse>> clubs){
        return GetClubsByHashTagResponse.builder()
                .hashtag(hashtag.getTitle())
                .clubs(clubs)
                .build();
    }
}