package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class GetClubPopularResponse {
    private Long clubId;
    private String clubName;
    private Long totalView;


    public static GetClubPopularResponse from(Club club) {
        return GetClubPopularResponse.builder()
                .clubId(club.getId())
                .clubName(club.getName())
                .totalView(club.getClubInfo().getTotalView())
                .build();
    }
}
