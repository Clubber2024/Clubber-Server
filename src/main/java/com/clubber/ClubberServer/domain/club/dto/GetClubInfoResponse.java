package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.domain.club.domain.ClubInfo;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubInfoResponse {
    private String instagram;
    private String leader;
    private Long room;
    private Long totalView;
    private String activity;



    public static GetClubInfoResponse from (ClubInfo clubinfo){
        return GetClubInfoResponse.builder()
                .instagram(clubinfo.getInstagram())
                .leader(clubinfo.getLeader())
                .room(clubinfo.getRoom())
                .totalView(clubinfo.getTotalView())
                .activity(clubinfo.getActivity())
                .build();
    }
}
