package com.clubber.ClubberServer.domain.club.dto;


import com.clubber.ClubberServer.domain.club.domain.Hashtag;
import lombok.*;
import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubsByHashTagResponse {

    private String hashtag;
    private List<GetClubByHashTagResponse> clubs;

    public static GetClubsByHashTagResponse of (Hashtag hashtag, List<GetClubByHashTagResponse> clubs){
        return GetClubsByHashTagResponse.builder()
                .hashtag(hashtag.getTitle())
                .clubs(clubs)
                .build();
    }
}