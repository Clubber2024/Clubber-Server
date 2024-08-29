package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.domain.club.domain.Department;
import lombok.*;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DepartmentSmallDto {
    private String department;
    private List<GetClubIntoCardResponse> clubs;

    @Builder
    public DepartmentSmallDto(Department department,List<GetClubIntoCardResponse> clubs){
        this.department=department.getTitle();
        this.clubs=clubs;
    }
}
