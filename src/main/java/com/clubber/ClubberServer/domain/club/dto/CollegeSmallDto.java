package com.clubber.ClubberServer.domain.club.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CollegeSmallDto {
    private String college;
    private List<DepartmentSmallDto> departmentClubs;

    @Builder
    public CollegeSmallDto(String college, List<DepartmentSmallDto> departmentClubs) {
        this.college=college;
        this.departmentClubs=departmentClubs;
    }

}
