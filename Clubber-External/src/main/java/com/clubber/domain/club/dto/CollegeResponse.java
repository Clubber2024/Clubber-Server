package com.clubber.domain.club.dto;

import com.clubber.domain.domains.club.domain.College;
import com.clubber.common.vo.enums.EnumMapperVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

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

    public static CollegeResponse from(College college, List<EnumMapperVO> departmentVOS) {
        return CollegeResponse.builder()
                .collegeCode(college.getCode())
                .collegeTitle(college.getTitle())
                .departments(departmentVOS)
                .build();
    }
}
