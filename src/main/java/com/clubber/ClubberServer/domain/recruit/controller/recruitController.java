package com.clubber.ClubberServer.domain.recruit.controller;

import com.clubber.ClubberServer.domain.recruit.dto.*;
import com.clubber.ClubberServer.domain.recruit.service.RecruitService;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Tag(name = "[모집글]")
public class recruitController {

    private final RecruitService recruitService;

    @GetMapping("/v1/clubs/{clubId}/recruit")
    @DisableSwaggerSecurity
    @Operation(summary = "특정 동아리 모집글 조회")
    public GetRecruitsByClubIdResponse getRecruitsByClubId(@PathVariable("clubId")Long clubId){
        return recruitService.getRecruitsByClubId(clubId);
    }

    @GetMapping("/v1/recruits")
    @DisableSwaggerSecurity
    @Operation(summary = "홍보 게시판에서 모든 모집글 조회")
    public GetAllRecruitsResponse getAllRecruitsPage(){
        return recruitService.getAllRecruitsPage();
    }

    @GetMapping("/v1/recruits/{recruitId}")
    @DisableSwaggerSecurity
    @Operation(summary = "홍보 게시판에서 개별 모집글 조회")
    public GetOneRecruitResponse getRecruitsByRecruitId(@PathVariable("recruitId")Long recruitId){
        return recruitService.getRecruitsByRecruitId(recruitId);
    }

}
