package com.clubber.ClubberServer.domain.club.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class HashtagDto { //각 분과별 dto
    private String hashtag;
    private List<SimpleCenterDto> clubs;

    @Builder
    public HashtagDto(String hashtag,List<SimpleCenterDto> clubs){
        this.hashtag=hashtag;
        this.clubs=clubs;
    }

}