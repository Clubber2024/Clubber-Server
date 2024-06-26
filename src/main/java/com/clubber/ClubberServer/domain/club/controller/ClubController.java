package com.clubber.ClubberServer.domain.club.controller;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.dto.CollegeSmallDto;
import com.clubber.ClubberServer.domain.club.dto.DepartmentSmallDto;
import com.clubber.ClubberServer.domain.club.dto.DivisionCenterDto;
import com.clubber.ClubberServer.domain.club.dto.OneClubDto;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.club.service.ClubService;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/clubs")
@Tag(name="[동아리/소모임]")
public class ClubController {

    private final ClubService clubService;


    // 중앙동아리 - 분과별 동아리 조회
    @DisableSwaggerSecurity
    @Operation(summary = "분과별 중앙동아리 조회")
    @GetMapping(params="division")
    public DivisionCenterDto getCentersByDivision(@RequestParam(name="division",required=false)String division){
        return clubService.getClubDivision(division);
    }


    // 소모임 - 학과별 소모임 조회
    @DisableSwaggerSecurity
    @Operation(summary = "학과별 소모임 조회")
    @GetMapping(params="department")
    public DepartmentSmallDto getSmallsByDepartment(@RequestParam(name="department",required=false)String department){
        return clubService.getOneDepartmentClubs(department);
    }



    /* === 중앙 동아리 & 소모임 공통 ===*/
    // 개별 동아리 및 소모임 페이지 조회
    @DisableSwaggerSecurity
    @Operation(summary = "동아리 및 소모임 개별 페이지 조회")
    @GetMapping("/{clubId}") //중앙동아리 및 소모임 개별 페이지 조회
    public OneClubDto getOneClubInfo(@PathVariable("clubId")Long clubId){
        return clubService.getIndividualPage(clubId);
    }

}
