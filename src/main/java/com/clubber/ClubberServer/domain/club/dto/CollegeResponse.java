package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.domain.club.domain.College;
import com.clubber.ClubberServer.global.enummapper.EnumMapperVO;
import lombok.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CollegeResponse {
    private String collegeCode;
    private String collegeTitle;
    private List<EnumMapperVO> departments;

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
