package com.clubber.ClubberServer.domain.club.controller;

import com.clubber.ClubberServer.domain.club.domain.College;
import com.clubber.ClubberServer.domain.club.domain.Department;
import com.clubber.ClubberServer.domain.club.domain.Division;
import com.clubber.ClubberServer.domain.club.dto.CollegeResponse;
import com.clubber.ClubberServer.domain.club.dto.DepartmentSmallDto;
import com.clubber.ClubberServer.domain.club.dto.GetClubByDivisionResponse;
import com.clubber.ClubberServer.domain.club.dto.GetClubPopularResponse;
import com.clubber.ClubberServer.domain.club.dto.GetClubResponse;
import com.clubber.ClubberServer.domain.club.dto.GetOfficialClubGroupResponse;
import com.clubber.ClubberServer.domain.club.dto.GetSummaryClubGroupResponse;
import com.clubber.ClubberServer.domain.club.service.ClubService;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;
import com.clubber.ClubberServer.global.vo.enums.EnumImageMapperVO;
import com.clubber.ClubberServer.global.vo.enums.EnumMapperVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clubs")
@Tag(name = "[동아리/소모임]")
public class ClubController {

    private final ClubService clubService;

    @DisableSwaggerSecurity
    @Operation(summary = "분과별 중앙동아리 조회")
    @GetMapping(params = "division")
    public GetClubByDivisionResponse getClubsByDivision(
        @RequestParam(name = "division", required = false) Division division) {
        return clubService.getClubsByDivision(division);
    }

    @DisableSwaggerSecurity
    @Operation(summary = "학과별 소모임 조회")
    @GetMapping(params = "department")
    public DepartmentSmallDto getClubsByDepartment(
        @RequestParam(name = "department", required = false) Department department) {
        return clubService.getClubsByDepartment(department);
    }

    @DisableSwaggerSecurity
    @Operation(summary = "동아리 및 소모임 개별 페이지 조회")
    @GetMapping("/{clubId}")
    public GetClubResponse getClubsIndividualPage(@PathVariable("clubId") Long clubId) {
        return clubService.getClubsIndividualPage(clubId);
    }

    @DisableSwaggerSecurity
    @Operation(summary = "메인페이지에서의 전체 동아리 해시태그 목록 조회")
    @GetMapping("/hashtags")
    public List<EnumImageMapperVO> getClubsTotalHashTags() {
        return clubService.getClubsTotalHashtags();
    }

    @DisableSwaggerSecurity
    @Operation(summary = "중앙동아리 - 분과 이름 목록 조회 \uD83D\uDCCB")
    @GetMapping("/divisions")
    public List<EnumMapperVO> getDivisions() {
        return clubService.getDivisionNames();
    }

    @DisableSwaggerSecurity
    @Operation(summary = "소모임 - 단과대별 학과 이름 목록 조회")
    @GetMapping("/colleges")
    public List<CollegeResponse> getColleges() {
        return clubService.getCollegesWithDepartments();
    }

    @DisableSwaggerSecurity
    @Operation(summary = "인기 순위 반환 개선용(추후 적용)")
    @GetMapping("/popular/temp")
    public List<GetClubPopularResponse> getPopularClubs() {
        return clubService.getClubsPopularTemp();
    }

    @DisableSwaggerSecurity
    @Operation(summary = "한눈에 보기")
    @GetMapping("/summary")
    public List<GetSummaryClubGroupResponse> getOneViewClubs() {
        return clubService.getSummaryClubs();
    }

    @DisableSwaggerSecurity
    @Operation(summary = "숭실대 공식 단체 조회")
    @GetMapping("/official")
    public GetOfficialClubGroupResponse getOfficialClubs() {
        return clubService.getOfficialClubs();
    }

    @DisableSwaggerSecurity
    @Operation(summary = "clubType 목록 반환 \uD83D\uDCCB")
    @GetMapping(value = "/types")
    public List<EnumMapperVO> getClubTypes() {
        return clubService.getClubTypes();
    }

    @DisableSwaggerSecurity
    @Operation(summary = "소모임 - 단과대 목록 조회 \uD83D\uDCCB")
    @GetMapping(value = "/colleges/types")
    public List<EnumMapperVO> getCollegeList() {
        return clubService.getColleges();
    }

    @DisableSwaggerSecurity
    @Operation(summary = "소모임 - 단과대 소속 학과 목록 조회 \uD83D\uDCCB")
    @GetMapping(value = "/departments", params = "college")
    public List<EnumMapperVO> getDepartmentList(@RequestParam(required = false) College college) {
        return clubService.getDepartmentList(college);
    }


}
