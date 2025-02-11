package com.clubber.ClubberServer.domain.club.controller;

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


    // 중앙동아리 - 분과별 동아리 조회
    @DisableSwaggerSecurity
    @Operation(summary = "분과별 중앙동아리 조회")
    @GetMapping(params = "division")
    public GetClubByDivisionResponse getClubsByDivision(
        @RequestParam(name = "division", required = false) Division division) {
        return clubService.getClubsByDivision(division);
    }


    // 소모임 - 학과별 소모임 조회
    @DisableSwaggerSecurity
    @Operation(summary = "학과별 소모임 조회")
    @GetMapping(params = "department")
    public DepartmentSmallDto getClubsByDepartment(
        @RequestParam(name = "department", required = false) Department department) {
        return clubService.getClubsByDepartment(department);
    }


    /* === 중앙 동아리 & 소모임 공통 ===*/
    // 개별 동아리 및 소모임 페이지 조회
    @DisableSwaggerSecurity
    @Operation(summary = "동아리 및 소모임 개별 페이지 조회")
    @GetMapping("/{clubId}") //중앙동아리 및 소모임 개별 페이지 조회
    public GetClubResponse getClubsIndividualPage(@PathVariable("clubId") Long clubId) {
        return clubService.getClubsIndividualPage(clubId);
    }

    @DisableSwaggerSecurity
    @Operation(summary = "메인페이지에서의 전체 동아리 해시태그 목록 조회")
    @GetMapping("/hashtags")
    public List<EnumMapperVO> getClubsTotalHashTags() {
        return clubService.getClubsTotalHashtags();
    }


    @DisableSwaggerSecurity
    @Operation(summary = "중앙동아리 - 분과 이름 목록 조회")
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

}
