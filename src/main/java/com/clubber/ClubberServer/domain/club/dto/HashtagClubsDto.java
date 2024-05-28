package com.clubber.ClubberServer.domain.club.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class HashtagClubsDto { //각 분과별 dto
    private String hashtag;
    private List<HashtagClubDto> clubs;

    @Builder
    public HashtagClubsDto(String hashtag,List<HashtagClubDto> clubs){
        this.hashtag=hashtag;
        this.clubs=clubs;
    }

}