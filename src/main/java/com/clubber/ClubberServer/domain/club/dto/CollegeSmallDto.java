package com.clubber.ClubberServer.domain.club.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CollegeSmallDto {
    private String college;
    private List<DepartmentSmallDto> departments;

    @Builder
    public CollegeSmallDto(String college, List<DepartmentSmallDto> departments) {
        this.college=college;
        this.departments=departments;
    }

}
