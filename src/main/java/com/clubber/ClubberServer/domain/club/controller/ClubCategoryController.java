package com.clubber.ClubberServer.domain.club.controller;

import com.clubber.ClubberServer.domain.club.domain.College;
import com.clubber.ClubberServer.domain.club.dto.CollegeResponse;
import com.clubber.ClubberServer.domain.club.service.ClubCategoyService;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;
import com.clubber.ClubberServer.global.vo.enums.EnumMapperVO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clubs/category")
public class ClubCategoryController {

    private final ClubCategoyService clubCategoyService;

    @DisableSwaggerSecurity
    @Operation(summary = "clubType 목록 반환 \uD83D\uDCCB")
    @GetMapping(value = "/types")
    public List<EnumMapperVO> getClubTypes() {
        return clubCategoyService.getClubTypes();
    }

    @DisableSwaggerSecurity
    @Operation(summary = "해시태그 목록 조회")
    @GetMapping("/hashtags")
    public List<EnumMapperVO> getClubsTotalHashTags() {
        return clubCategoyService.getHashtags();
    }

    @DisableSwaggerSecurity
    @Operation(summary = "중앙동아리 - 분과 이름 목록 조회 \uD83D\uDCCB")
    @GetMapping("/divisions")
    public List<EnumMapperVO> getDivisions() {
        return clubCategoyService.getDivisions();
    }

    @DisableSwaggerSecurity
    @Operation(summary = "소모임 - 전체 단과대별 소속 학과 목록 조회")
    @GetMapping("/colleges")
    public List<CollegeResponse> getColleges() {
        return clubCategoyService.getCollegesWithDepartments();
    }

    @DisableSwaggerSecurity
    @Operation(summary = "소모임 - 단과대 목록 조회 \uD83D\uDCCB")
    @GetMapping(value = "/colleges/types")
    public List<EnumMapperVO> getCollegeList() {
        return clubCategoyService.getColleges();
    }

    @DisableSwaggerSecurity
    @Operation(summary = "소모임 - 단과대별 소속 학과 목록 조회 \uD83D\uDCCB")
    @GetMapping(value = "/departments", params = "college")
    public List<EnumMapperVO> getDepartmentList(@RequestParam(required = false) College college) {
        return clubCategoyService.getDepartmentsByCollege(college);
    }
}
