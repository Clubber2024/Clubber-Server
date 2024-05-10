package com.clubber.ClubberServer.domain.club.controller;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.dto.CollegeSmallDto;
import com.clubber.ClubberServer.domain.club.dto.DepartmentSmallDto;
import com.clubber.ClubberServer.domain.club.dto.DivisionCenterDto;
import com.clubber.ClubberServer.domain.club.dto.OneClubDto;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.club.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/clubs")
public class ClubController {
    private final ClubRepository clubRepository;
    private final ClubService clubService;

//    @GetMapping //중앙동아리 한눈에 보기 조회
//    public ResponseEntity<List<DivisionCenterDto>> getAllCenters(){
//        List<DivisionCenterDto> divisions=clubService.getClubsGroupedByDivision();
//        return ResponseEntity.ok(divisions);
//    }

    // 중앙동아리 - 분과별 동아리 조회
    @GetMapping(params="division")
    public ResponseEntity<DivisionCenterDto> getCentersByDivision(@RequestParam("division")String division){
        DivisionCenterDto curr=clubService.getClubDivision(division);
        return ResponseEntity.ok(curr);

    }


//    //소모임 단과대별 소모임 조회
//    @GetMapping(params="college")
//    public ResponseEntity<CollegeSmallDto> getSmallsByCollege(@RequestParam("college")String college){
//        CollegeSmallDto curr=clubService.getSmallCollege(college);
//        return ResponseEntity.ok(curr);
//    }

    // 소모임 - 학과별 소모임 조회
    @GetMapping(params="department")
    public ResponseEntity<DepartmentSmallDto> getSmallsByDepartment(@RequestParam("department")String department){
        DepartmentSmallDto curr=clubService.getOneDepartmentClubs(department);
        return ResponseEntity.ok(curr);
    }



    /* === 중앙 동아리 & 소모임 공통 ===*/
    // 개별 동아리 및 소모임 페이지 조회
    @GetMapping("/{clubId}") //중앙동아리 및 소모임 개별 페이지 조회
    public ResponseEntity<OneClubDto> getOneClubInfo(@PathVariable("clubId")Long clubId){
        OneClubDto curr=clubService.individualPage(clubId);
        return ResponseEntity.ok(curr);
    }

}
