package com.clubber.ClubberServer.domain.club.controller;

import com.clubber.ClubberServer.domain.club.domain.College;
import com.clubber.ClubberServer.domain.club.dto.CollegeResponse;
import com.clubber.ClubberServer.domain.club.service.ClubCategoryService;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;
import com.clubber.ClubberServer.global.vo.enums.EnumMapperVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clubs/category")
@Tag(name = "[동아리/소모임] 관련 카테고리 조회 모음")
public class ClubCategoryController {

    private final ClubCategoryService clubCategoryService;

    @DisableSwaggerSecurity
    @Operation(summary = "clubType 목록 반환 \uD83D\uDCCB")
    @GetMapping(value = "/club-types")
    public List<EnumMapperVO> getClubTypes() {
        return clubCategoryService.getClubTypes();
    }

    @DisableSwaggerSecurity
    @Operation(summary = "해시태그 목록 조회")
    @GetMapping("/hashtags")
    public List<EnumMapperVO> getClubsTotalHashTags() {
        return clubCategoryService.getHashtags();
    }

    @DisableSwaggerSecurity
    @Operation(summary = "중앙동아리 - 분과 이름 목록 조회 \uD83D\uDCCB")
    @GetMapping("/divisions")
    public List<EnumMapperVO> getDivisions() {
        return clubCategoryService.getDivisions();
    }

    @DisableSwaggerSecurity
    @Operation(summary = "소모임 - 전체 단과대 목록 + 소속 학과 목록 조회")
    @GetMapping("/colleges/with-departments")
    public List<CollegeResponse> getColleges() {
        return clubCategoryService.getCollegesWithDepartments();
    }

    @DisableSwaggerSecurity
    @Operation(summary = "소모임 - 전체 단과대 목록 조회 \uD83D\uDCCB")
    @GetMapping(value = "/colleges")
    public List<EnumMapperVO> getCollegeList() {
        return clubCategoryService.getColleges();
    }

    @DisableSwaggerSecurity
    @Operation(summary = "소모임 - 단과대별 소속 학과 목록 조회 \uD83D\uDCCB")
    @GetMapping(value = "/departments", params = "college")
    public List<EnumMapperVO> getDepartmentList(@RequestParam(required = false) College college) {
        return clubCategoryService.getDepartmentsByCollege(college);
    }
}
