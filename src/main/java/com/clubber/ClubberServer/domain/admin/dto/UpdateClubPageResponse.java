package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.domain.ClubInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateClubPageResponse {
    private String imageUrl;
    private String introduction;
    private String instagram;
    private String activity;
    private String leader;
    private Long room;

    public static UpdateClubPageResponse of(Club club, ClubInfo clubInfo){
        return UpdateClubPageResponse.builder()
                .imageUrl(club.getImageUrl())
                .introduction(club.getIntroduction())
                .instagram(clubInfo.getInstagram())
                .activity(clubInfo.getActivity())
                .leader(clubInfo.getLeader())
                .room(clubInfo.getRoom())
                .build();
    }

}