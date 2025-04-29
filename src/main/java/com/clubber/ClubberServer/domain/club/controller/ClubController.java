package com.clubber.ClubberServer.domain.club.controller;

import com.clubber.ClubberServer.domain.club.domain.Department;
import com.clubber.ClubberServer.domain.club.domain.Division;
import com.clubber.ClubberServer.domain.club.domain.Hashtag;
import com.clubber.ClubberServer.domain.club.dto.*;
import com.clubber.ClubberServer.domain.club.service.ClubService;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Operation(summary = "해시태그별 동아리/소모임 조회")
    @GetMapping(params = "hashtag")
    public GetClubsByHashTagResponse searchByHashtag(
            @RequestParam(name = "hashtag", required = false) Hashtag hashtag) {
        return clubService.getClubsHashtag(hashtag);
    }

    @DisableSwaggerSecurity
    @Operation(summary = "동아리 및 소모임 개별 페이지 조회")
    @GetMapping("/{clubId}")
    public GetClubResponse getClubsIndividualPage(@PathVariable("clubId") Long clubId) {
        return clubService.getClubsIndividualPage(clubId);
    }

    @DisableSwaggerSecurity
    @Operation(summary = "한눈에 보기")
    @GetMapping("/summary")
    public List<GetSummaryClubGroupResponse> getOneViewClubs() {
        return clubService.getSummaryClubs();
    }

    @DisableSwaggerSecurity
    @Operation(summary="조회수 기반 인기 순위 조회")
    @GetMapping("/popular")
    public List<GetClubPopularResponse> getClubsPopular(){
        return clubService.getClubsPopular();
    }

    @DisableSwaggerSecurity
    @Operation(summary = "숭실대 공식 단체 조회")
    @GetMapping("/official")
    public GetOfficialClubGroupResponse getOfficialClubs() {
        return clubService.getOfficialClubs();
    }

    /**TODO
     * 추후 수정
     */
    @DisableSwaggerSecurity
    @Operation(summary = "인기 순위 반환 개선용(추후 적용)")
    @GetMapping("/popular/temp")
    public List<GetClubPopularResponse> getPopularClubs() {
        return clubService.getClubsPopularTemp();
    }
}
