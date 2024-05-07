package com.clubber.ClubberServer.domain.club.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class DivisionCenterDto { //각 분과별 dto
    private String division;
    private List<SimpleCenterDto> clubs;

    @Builder
    public DivisionCenterDto(String division,List<SimpleCenterDto> clubs){
        this.division=division;
        this.clubs=clubs;
    }

}
