package com.clubber.ClubberServer.domain.club.dto;


import com.clubber.ClubberServer.domain.club.domain.Hashtag;
import lombok.*;
import java.util.List;
import java.util.Map;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubsByHashTagResponse { //각 분과별 dto
    private String hashtag;

    Map<String, List<GetClubByHashTagResponse>> clubs;

    public static GetClubsByHashTagResponse of (Hashtag hashtag,Map<String, List<GetClubByHashTagResponse>> clubs){
        return GetClubsByHashTagResponse.builder()
                .hashtag(hashtag.getTitle())
                .clubs(clubs)
                .build();
    }
}