package com.clubber.ClubberServer.domain.club.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
public class SearchClubsDto {
    private String clubName;
    private List<SearchClubDto> clubs;

    @Builder
    public SearchClubsDto(String clubName,List<SearchClubDto> clubs){
        this.clubName=clubName;
        this.clubs=clubs;
    }

}