package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.domain.club.domain.Department;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class DepartmentSmallDto {
    private Department department;
    private List<GetClubIntoCardResponse> clubs;

    @Builder
    public DepartmentSmallDto(Department department,List<GetClubIntoCardResponse> clubs){
        this.department=department;
        this.clubs=clubs;
    }
}
