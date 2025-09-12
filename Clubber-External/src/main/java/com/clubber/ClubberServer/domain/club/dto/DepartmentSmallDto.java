package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.domain.domains.club.domain.Department;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DepartmentSmallDto {

    @Schema(description = "학과명", example = "소프트웨어학부")
    private final String department;

    @Schema(description = "학과 소속 동아리 목록")
    private final List<GetClubIntoCardResponse> clubs;

    @Builder
    public DepartmentSmallDto(Department department,List<GetClubIntoCardResponse> clubs){
        this.department=department.getTitle();
        this.clubs=clubs;
    }
}
