package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.domain.club.domain.College;
import com.clubber.ClubberServer.global.vo.enums.EnumMapperVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CollegeResponse {

    @Schema(description = "단과대 코드", example = "IT_COLLEGE")
    private final String collegeCode;

    @Schema(description = "단과대명", example = "IT대학")
    private final String collegeTitle;

    @Schema(description = "단과대 소속 학과들")
    private final List<EnumMapperVO> departments;

    public static CollegeResponse from(College college) {

        List<EnumMapperVO> departments = college.getDepartments().stream()
                .map(department -> new EnumMapperVO(department))
                .collect(Collectors.toList());

        return CollegeResponse.builder()
                .collegeCode(college.getCode())
                .collegeTitle(college.getTitle())
                .departments(departments)
                .build();
    }
}
