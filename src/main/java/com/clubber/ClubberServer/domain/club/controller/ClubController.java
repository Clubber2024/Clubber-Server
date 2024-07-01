package com.clubber.ClubberServer.domain.club.controller;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.domain.College;
import com.clubber.ClubberServer.domain.club.dto.*;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.club.service.ClubService;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;
import com.clubber.ClubberServer.global.enummapper.EnumMapperVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/clubs")
@Tag(name="[동아리/소모임]")
public class ClubController {

    private final ClubService clubService;

    @DisableSwaggerSecurity
    @Operation(summary = "중앙동아리 - 분과 이름 목록 조회")
    @GetMapping("/divisions")
    public List<EnumMapperVO> getDivisions() {
        return clubService.getDivisionNames();
    }


    @DisableSwaggerSecurity
    @Operation(summary = "중앙동아리 - 분과별 중앙동아리 조회")
    @GetMapping(params="division")
    public DivisionCenterDto getCentersByDivision(@RequestParam(name="division",required=false)String division){
        return clubService.getClubDivision(division);
    }



    @DisableSwaggerSecurity
    @Operation(summary = "소모임 - 단과대별 학과 이름 목록 조회")
    @GetMapping("/colleges")
    public List<CollegeDTOResponse> getColleges() {
        return clubService.getCollegesWithDepartments();
    }



    @DisableSwaggerSecurity
    @Operation(summary = "학과별 소모임 조회")
    @GetMapping(params="department")
    public DepartmentSmallDto getSmallsByDepartment(@RequestParam(name="department",required=false)String department){
        return clubService.getOneDepartmentClubs(department);
    }



    /* === 중앙 동아리 & 소모임 공통 ===*/
    @DisableSwaggerSecurity
    @Operation(summary = "동아리 및 소모임 개별 페이지 조회")
    @GetMapping("/{clubId}") //중앙동아리 및 소모임 개별 페이지 조회
    public OneClubDto getOneClubInfo(@PathVariable("clubId")Long clubId){
        return clubService.getIndividualPage(clubId);
    }

}
