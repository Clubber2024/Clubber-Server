package com.clubber.ClubberServer.domain.club.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class DepartmentSmallDto {
    private String department;
    private List<GetClubIntoCardResponse> clubs;

    @Builder
    public DepartmentSmallDto(String department,List<GetClubIntoCardResponse> clubs){
        this.department=department;
        this.clubs=clubs;
    }
}
