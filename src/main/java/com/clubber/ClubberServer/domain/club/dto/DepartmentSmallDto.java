package com.clubber.ClubberServer.domain.club.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class DepartmentSmallDto {
    private String department;
    private List<SimpleCenterDto> clubs;

    @Builder
    public DepartmentSmallDto(String department,List<SimpleCenterDto> clubs){
        this.department=department;
        this.clubs=clubs;
    }
}
